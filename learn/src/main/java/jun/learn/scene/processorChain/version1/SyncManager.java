package jun.learn.scene.processorChain.version1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import jun.learn.scene.processorChain.util.ReflectUtil;

@SuppressWarnings("unchecked")
public class SyncManager{
	private Map<String, Listener<?>> listeners = new HashMap<String, Listener<?>>();
	private Convertor convertor = new StandardConvertor();
	private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	private Map<String, DataProcessor<?>> processors = new HashMap<String, DataProcessor<?>>();
	
	public void process(String name, DataProvider dataProvider) {
		process0(name, dataProvider, getClassByName(name));
	}
	
	private <T> void process0(String name, DataProvider dataProvider, Class<T> clazz) {
		List<T> data = convertor.convert(dataProvider, clazz);
		Listener<T> listener = (Listener<T>) listeners.get(name);
		if (listener != null) {
			listener.preHandle(data);
		}
		
		ObjectProcessorChain<T> processorChain = buildChain(name);
		for (T t : data) {
			processorChain.process(t);
		}
	}
	
	public void registerListener(Listener<?> listener) {
		listeners.put(listener.getName(), listener);
	}
	
	public void registerProcessor(DataProcessor<?> processor) {
		processors.put(processor.getName(), processor);
		classMap.put(processor.getName(), ReflectUtil.getGenericClass(processor.getClass()));
	}
	
	private <T> ObjectProcessorChain<T> buildChain(String name) {
		ObjectProcessorChain<T> processChain = new StandardObjectProcessorChain<T>();
		processChain.addProcessor(new CheckerProcessor<T>());
		processChain.addProcessor((DataProcessor<T>) processors.get(name));
		return processChain;
	}
	
	private Class<?> getClassByName(String name) {
		return classMap.get(name);
	}
	
	private static class StandardObjectProcessorChain<T> implements ObjectProcessorChain<T> {
		private List<DataProcessor<T>> processors = new ArrayList<DataProcessor<T>>();
		private SimpleContext ctx = new SimpleContext();
		
		@Override
		public void process(T t) {
			ctx.refresh();
			ctx.invokeNext(t);
		}

		@Override
		public void addProcessor(DataProcessor<T> processor) {
			processors.add(processor);
		}
		
		private class SimpleContext extends JSONObject implements Context<T>{
			/**
			 * 
			 */
			private int index = 0;
			private static final long serialVersionUID = 1L;
			
			public void refresh() {
				index = 0;
			}
			
			public void invokeNext(T t) {
				if (index >= processors.size()) {
					return;
				}
				processors.get(index++).process(t, this);
			}
		}
	}
	
	private static class StandardConvertor implements Convertor{
		@Override
		public <T> List<T> convert(DataProvider dataProvider, Class<T> clazz) {
			List<T> list = new ArrayList<T>();
			while (dataProvider.hasNext()) {
				list.add(convert(dataProvider.next(), clazz));
			}
			return list;
		}
		
		private <T> T convert(Map<String, Object> data, Class<T> clazz) {
			Object target = ReflectUtil.newIntance(clazz);
			for (String key : data.keySet()) {
				ReflectUtil.fillField(ReflectUtil.getField(clazz, key), target, data.get(key));
			}
			return (T) target;
		}
	}
}