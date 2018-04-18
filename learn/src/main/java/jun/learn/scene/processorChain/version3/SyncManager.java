package jun.learn.scene.processorChain.version3;

import java.util.HashMap;
import java.util.Map;

import jun.learn.scene.processorChain.util.ReflectUtil;
import jun.learn.scene.processorChain.version3.DataProvider.Consumer;

public class SyncManager{
	private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	private Map<String, DataProcessor<?>> processors = new HashMap<String, DataProcessor<?>>();
	
	public void process(DataProvider dataProvider) {
		Class<?> clazz = classMap.get(dataProvider.getName());
		process0(dataProvider.getName(), dataProvider, clazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> void process0(String name, DataProvider dataProvider, Class<T> clazz) {
		Context ctx = new SimpleContext();
		DataProcessor<T> processor = (DataProcessor<T>) processors.get(name);
		processor.preProcess(dataProvider, ctx);
		dataProvider.each(new Consumer<T>() {
			public void consume(T t) {
				processor.process(t, ctx);
			}
		}, clazz);
	}
	
	public void registerProcessor(DataProcessor<?> processor) {
		processors.put(processor.getName(), processor);
		classMap.put(processor.getName(), ReflectUtil.getGenericClass(processor.getClass()));
	}
	
	private static class SimpleContext implements Context{
		private Map<String, Object> map = new HashMap<String, Object>();
		
		@Override
		public void put(String key, Object value) {
			map.put(key, value);
		}

		@Override
		public Object get(String key) {
			return map.get(key);
		}

		@Override
		public <T> T get(String key, Class<T> t) {
			return t.cast(map.get(key));
		}
	}
}