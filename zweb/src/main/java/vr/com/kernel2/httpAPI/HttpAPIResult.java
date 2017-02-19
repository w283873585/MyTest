package vr.com.kernel2.httpAPI;

import java.util.Map;
import java.util.function.BiConsumer;

public interface HttpAPIResult {
	
	public default boolean isSuccess() {
		return getCode() == 200;
	}
	
	public int getCode();
	
	public String getAll();
	
	public String getBody();
	
	public Map<String, Object> getHeader();
	
	public Object get(String key);
	
	public void foreach(BiConsumer<String, Object> consumer);
}
