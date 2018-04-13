package jun.learn.scene.processorChain.version3;

import java.util.HashMap;
import java.util.Map;

import jun.learn.scene.processorChain.util.ReflectUtil;

public class SyncManager{
	private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	private Map<String, DataProcessor<?>> processors = new HashMap<String, DataProcessor<?>>();
	
	public void process(DataProvider dataProvider) {
		Class<?> clazz = classMap.get(dataProvider.getName());
		process0(dataProvider.getName(), dataProvider, clazz);
	}
	
	@SuppressWarnings("unchecked")
	private <T> void process0(String name, DataProvider dataProvider, Class<T> clazz) {
		DataProcessor<T> processor = (DataProcessor<T>) processors.get(name);
		processor.beforeProcess(dataProvider);
		dataProvider.each(entity -> {
			processor.process(entity);
		}, clazz);
	}
	
	public void registerProcessor(DataProcessor<?> processor) {
		processors.put(processor.getName(), processor);
		classMap.put(processor.getName(), ReflectUtil.getGenericClass(processor.getClass()));
	}
}