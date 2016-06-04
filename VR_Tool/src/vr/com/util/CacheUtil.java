package vr.com.util;

import java.util.Properties;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
	
	public static String toJson() {
		JSONArray arr = new JSONArray();
		for (Object key : properties.keySet()) {
			String result = properties.getProperty((String) key);
			JSONObject obj = JSON.parseObject(result);
			obj.put("id", key);
			arr.add(obj);
		}
		return arr.toJSONString();
	}
}
