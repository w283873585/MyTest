package jun.learn.scene.processorChain.version2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jun.learn.scene.processorChain.util.ReflectUtil;

@SuppressWarnings("unchecked")
public class SyncManager{
	private Convertor convertor = new StandardConvertor();
	private Map<String, Class<?>> classMap = new HashMap<String, Class<?>>();
	private Map<String, DataProcessor<?>> processors = new HashMap<String, DataProcessor<?>>();
	
	public void process(String name, DataProvider dataProvider) {
		process0(name, dataProvider, getClassByName(name));
	}
	
	private <T> void process0(String name, DataProvider dataProvider, Class<T> clazz) {
		List<T> data = convertor.convert(dataProvider, clazz);
		
		DataProcessor<T> processor = (DataProcessor<T>) processors.get(name);
		processor.beforeProcess(data);
		for (T t : data) {
			processor.process(t);
		}
	}
	
	public void registerProcessor(DataProcessor<?> processor) {
		processors.put(processor.getName(), processor);
		classMap.put(processor.getName(), ReflectUtil.getGenericClass(processor.getClass()));
	}
	
	private Class<?> getClassByName(String name) {
		return classMap.get(name);
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