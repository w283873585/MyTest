package jun.learn.scene.sync;

import java.util.HashMap;
import java.util.Map;

public class Synchronizer {
	public void sync(String name, Integer type) {
		/**
		 * 1. 验证
		 * 2. 转换
		 * 3. 逻辑处理
		 */
		DataProvider provider = getDataProvider(name, type);
		final ProcessorFactory factory = new ProcessorFactory();
		final DataProcessor processor = factory.getProcessor(name);
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
	
	public interface DataProcessor {
		String getName();
		void process(Map<String, Object> data);
	}
	
	public static abstract class ObjectProcessor<T> implements DataProcessor{
		
		@SuppressWarnings("unchecked")
		public void process(Map<String, Object> data) {
			T finalVal = convert(data, (Class<T>) ReflectUtil.getGenericClass(this.getClass()));
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
		
		public abstract void process(T t);
	}
	
	public static class ProcessorFactory{
		private Map<String, DataProcessor> processorMap = new HashMap<String, DataProcessor>();
		{
			register(new AProcessor());
		}
		
		public DataProcessor getProcessor(String name) {
			return processorMap.get(name);
		}
		
		public void register(DataProcessor processor) {
			processorMap.put(processor.getName(), processor);
		}
	}
	
	public static class AProcessor extends ObjectProcessor<Req>{

		@Override
		public String getName() {
			return "a";
		}

		@Override
		public void process(Req t) {
			System.out.println("----------->" + t.getKey());
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