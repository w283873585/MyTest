package vr.com.kernel2.http;

import java.util.Map;

public interface Response {
	
	public int getCode();
	
	public String getAll();
	
	public String getBody();
	
	public Map<String, Object> getHeader();
}
