package vr.com.kernel;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class Factory <T extends Denominative> {
	private boolean initialized = false;
	private Map<String, T> data = new HashMap<String, T>();
	
	public void initialize(@SuppressWarnings("unchecked") T ... d) {
		if (initialized) {
			throw new RuntimeException("已经初始化");
		}
		initialized = true;
		
		for (T t : d) {
			data.put(t.getName(), t);
		}
	}
	
	public T get(String key) {
		if (!initialized) {
			throw new RuntimeException("未初始化");
		}
		return data.get(key);
	}
	
	public Set<String> keyset() {
		if (!initialized) {
			throw new RuntimeException("未初始化");
		}
		return data.keySet();
	}
}
