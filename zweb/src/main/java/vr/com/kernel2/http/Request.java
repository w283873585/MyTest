package vr.com.kernel2.http;

import java.util.Map;

public interface Request {
	
	public String getUrl();
	
	public boolean isGet();
	
	public String getCharset();
	
	public byte[] getData();
	
	public void addBody(String key, Object value);
	
	public boolean isJsonBody();
	
	public Map<String, Object> addHeader(String key, Object value);
	
	public Map<String, Object> getHeader();
	
}
