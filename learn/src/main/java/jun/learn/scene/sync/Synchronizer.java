package jun.learn.scene.sync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jun.learn.scene.sync.Synchronizer.ObjectChainProcessor.Context;

public class Synchronizer {
	public void sync(String name, Integer type) {
		/**
		 * 1. 验证
		 * 2. 转换
		 * 3. 逻辑处理
		 */
		DataProvider provider = getDataProvider(name, type);
		final ObjectChainProcessor<?> processor = ObjectChainProcessor
				.buildChain(name, ProcessorFactory.getClassByName(name));
		provider.foreach(new Consumer() {
			@Override
			public void apply(Map<String, Object> data) {
				/**
				 *  TODO 
				 *  1. 自定义策略拦截
				 *  2. 错误提示策略
				 *  3. 业务拆分 ???
				 *  4. 处理已删除数据
				 */
				processor.process(data);
			}
		});
		
		
		/*
		1.  数据提供者
		{
			2. 验证者
			3. 逻辑处理者
		}
		*/
	}
	
	private DataProvider getDataProvider(String name, Integer type) {
		return new DataProvider() {
			@Override
			public void foreach(Consumer con) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("key", "value");
				con.apply(map);
			}
		};
	}
	
	public interface DataProvider{
		void foreach(Consumer con);
	}
	
	public interface Consumer{
		void apply(Map<String, Object> map);
	}
	
	public static class ObjectChainProcessor<T> {
		public static <T> ObjectChainProcessor<T> buildChain(String name, Class<T> c) {
			return new ObjectChainProcessor<T>(name, c);
		}
		
		private Class<T> reqClass;
		private Context<T> ctx = new Context<T>();
		
		public ObjectChainProcessor(String name, Class<T> c) {
			this.reqClass = c;
			ctx.add(new CheckerProcessor<T>());
			ctx.add(ProcessorFactory.getProcessors(name));
		}
		
		public void process(Map<String, Object> data) {
			T finalVal = convert(data, reqClass);
			process(finalVal);
		}
		
		@SuppressWarnings("unchecked")
		private T convert(Map<String, Object> data, Class<T> clazz) {
			Object target = ReflectUtil.newIntance(clazz);
			for (String key : data.keySet()) {
				ReflectUtil.fillField(ReflectUtil.getField(clazz, key), target, data.get(key));
			}
			return (T) target;
		}
		
		public void process(T t) {
			ctx.init();
			ctx.invokeNext(t);
		}
		
		public static class Context<T>{
			private int index = 0;
			private List<DataProcessor<T>> processors = new ArrayList<DataProcessor<T>>();
			private Map<String, Object> body = new HashMap<String, Object>();
			
			public void init() {
				index = 0;
			}
			
			public void invokeNext(T t) {
				if (index >= processors.size()) {
					return;
				}
				processors.get(index++).process(t, this);
			}
			
			public void add(List<DataProcessor<T>> processorList) {
				for (DataProcessor<T> processor : processorList) {
					add(processor);
				}
			}
			
			public void add(DataProcessor<T> processor) {
				processors.add(processor);
			}
			public void put(String key, Object value) {
				body.put(key, value);
			}
			public void remove(String key) {
				body.remove(key);
			}
			public Object get(String key) {
				return body.get(key);
			}
		}
	}
	
	public interface DataProcessor<T>{
		public String getName();
		public void process(T t, Context<T> ctx);
	}
	
	public static class CheckerProcessor<T> implements DataProcessor<T> {
		@Override
		public void process(T t, Context<T> ctx) {
			ctx.invokeNext(t);
		}

		@Override
		public String getName() {
			return null;
		}
	}
	
	public static class ProcessorFactory{
		private static Map<String, List<DataProcessor<?>>> processorMap = new HashMap<String, List<DataProcessor<?>>>();
		private static Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
		static {
			register(new AProcessor());
		}
		
		@SuppressWarnings("unchecked")
		public static <T> List<DataProcessor<T>> getProcessors(String name) {
			List<DataProcessor<?>> list = processorMap.get(name);
			List<DataProcessor<T>> result = new ArrayList<DataProcessor<T>>(); 
			for (int i = 0; i < list.size(); i++) {
				result.add((DataProcessor<T>) list.get(i));
			}
			return result;
		}
		
		public static void register(DataProcessor<?> processor) {
			if (!processorMap.containsKey(processor.getName())) {
				List<DataProcessor<?>> list = new ArrayList<DataProcessor<?>>();
				list.add(processor);
				classMap.put(processor.getName(), ReflectUtil.getGenericClass(processor.getClass()));
				processorMap.put(processor.getName(), list);
			} else {
				List<DataProcessor<?>> list = processorMap.get(processor.getName());
				list.add(processor);
			}
		}
		
		public static Class<?> getClassByName(String name) {
			return classMap.get(name);
		}
	}
	
	public static class AProcessor implements DataProcessor<Req>{
		@Override
		public String getName() {
			return "a";
		}

		@Override
		public void process(Req t, Context<Req> ctx) {
			System.out.println(t.getKey());
		}
	}
	
	public static class Req{
		private String key;

		public String getKey() {
			return key;
		}

		public void setKey(String key) {
			this.key = key;
		}
	}
}