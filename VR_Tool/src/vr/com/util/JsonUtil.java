package vr.com.util;

import com.alibaba.fastjson.JSONObject;

public class JsonUtil {
	public static class MyJson extends JSONObject {
		private MyJson() {}
		private static final long serialVersionUID = 1L;
		public MyJson putA(String key, Object val) {
			put(key, val);
			return this;
		}
	}
	public static MyJson putA(String key, Object val) {
		return new MyJson().putA(key, val);
	}
	public static MyJson newMap() {
		return new MyJson();
	}
}
