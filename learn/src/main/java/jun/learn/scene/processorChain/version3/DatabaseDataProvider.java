package jun.learn.scene.processorChain.version3;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jun.learn.scene.processorChain.util.ReflectUtil;

public class DatabaseDataProvider extends AbstractDataProvider<Map<String, Object>> implements DataProvider {
	private String name;
	private List<Map<String, Object>> list = null;
	
	public DatabaseDataProvider(String name) {
		this.name = name;
		list = new ArrayList<Map<String, Object>>();
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("name", "小芳");
		list.add(data);
		Map<String, Object> data1 = new HashMap<String, Object>();
		data1.put("name", "小李");
		list.add(data1);
		Map<String, Object> data2 = new HashMap<String, Object>();
		data2.put("name", "小妹");
		list.add(data2);
		Map<String, Object> data3 = new HashMap<String, Object>();
		data3.put("name", "小鱼");
		list.add(data3);
	}

	@Override
	public List<Map<String, Object>> getList() {
		return list;
	}

	@Override
	public String getName() {
		return name;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T convert(Map<String, Object> data, Class<T> clazz) {
		Object target = ReflectUtil.newIntance(clazz);
		for (String key : data.keySet()) {
			ReflectUtil.fillField(ReflectUtil.getField(clazz, key), target, data.get(key));
		}
		return (T) target;
	}

	@Override
	public <T> Convertor<T, Map<String, Object>> getConvertor() {
		return new StandardConvertor<T>();
	}
	
	public static class StandardConvertor<T> implements Convertor<T, Map<String, Object>>{
		@SuppressWarnings("unchecked")
		@Override
		public T convert(Map<String, Object> data, Class<T> clazz) {
			Object target = ReflectUtil.newIntance(clazz);
			for (String key : data.keySet()) {
				ReflectUtil.fillField(ReflectUtil.getField(clazz, key), target, data.get(key));
			}
			return (T) target;
		}
	}
}