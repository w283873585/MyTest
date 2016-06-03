package vr.com.request;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.alibaba.fastjson.JSONObject;

public class Request {
	private String charset = "utf-8";
	private String requestUrl;
	private boolean isGet;
	private boolean isJsonBody = false;
	private Map<String, String> header;
	private Map<String, Object> params;
	
	public Request(boolean isGet,
			String requestUrl,
			boolean isJsonBody,
			Map<String, String> header, 
			Map<String, Object> params) {
		
		this.isGet = isGet;
		this.requestUrl = requestUrl;
		this.isJsonBody = isJsonBody;
		this.header = header;
		this.params = params;
	}
	
	public Request(boolean isGet,
			String requestUrl,
			Map<String, String> header, 
			Map<String, Object> params) {
		
		this.isGet = isGet;
		this.requestUrl = requestUrl;
		this.header = header;
		this.params = params;
	}
	
	public Request(boolean isGet,
			String requestUrl,
			Map<String, Object> params) {
		
		this.isGet = isGet;
		this.params = params;
		this.requestUrl = requestUrl;
		this.header = new HashMap<String, String>();
	}
	
	public void setJsonBody(boolean isJsonBody) {
		this.isJsonBody = isJsonBody;
	}
	
	public Map<String, Object> getParams() {
		return params;
	}
	
	public void addParam(String key, Object value) {
		params.put(key, value);
	}
	
	public void removeParam(String key, Object value) {
		params.remove(key);
	}
	
	public void setParam(Map<String, Object> map) {
		this.params = map;
	}
	
	public void clearParam() {
		params = new HashMap<String, Object>();
	}
	
	public String getUrl() {
		return requestUrl;
	}
	
	public void addHeader(String key, String value) {
		header.put(key, value);
	}
	
	public Map<String, String> getHeaders() {
		return header;
	}
	
	public boolean isGet() {
		return isGet;
	}
	
	public void setGet(boolean isGet) {
		this.isGet = isGet;
	}
	
	public byte[] getData() {
		return	mapToByteData(params);
	}
	
	private byte[] mapToByteData(Map<String, Object> params) {
		if (params == null || params.size() == 0) {
			return null;
		}
		try {
			String result;
			if (isJsonBody) {
				result = JSONObject.toJSONString(params);
			} else {
				StringBuffer sb = new StringBuffer();
				for (Entry<String, Object> e : params.entrySet()) {
					sb.append(e.getKey());
					sb.append("=");
					sb.append(URLEncoder.encode(e.getValue().toString(), charset));
					sb.append("&");
				}
				sb.deleteCharAt(sb.length() - 1);
				result = sb.toString();
			}
			return result.getBytes(charset);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
}
