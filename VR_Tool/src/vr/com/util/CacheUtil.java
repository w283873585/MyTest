package vr.com.util;

import java.util.Properties;

import com.alibaba.fastjson.JSONObject;

public class CacheUtil {
	private static final String FILENAME = "request_cache.properties";
	
	private static int index;
	private static Properties properties = new Properties();
	
	static {
		try {
			properties.load(CacheUtil.class.getClassLoader().getResourceAsStream(FILENAME));
			index = properties.size();
		} catch (Exception ignore){}
	}
	
	public synchronized static int add(String value) {
		String key = "" + (index++);
		properties.setProperty(key , value);
		PersistUtil.cacheRequest(key, value);
		return index;
	}
	
	public static String getJson() {
		return JSONObject.toJSONString(properties);
	}
}
