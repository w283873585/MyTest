package vr.com.util;

import com.alibaba.fastjson.JSONObject;

public class JSONMapUtil {
	
	public static class MyJsonMap extends JSONObject {
		/**
		 * 
		 */
		private static final long serialVersionUID = -1251947343462982222L;

		private MyJsonMap() {}
		
		public MyJsonMap putA(String key, Object val) {
			put(key, val);
			return this;
		}
	}
	
	public static MyJsonMap putA(String key, Object val) {
		return new MyJsonMap().putA(key, val);
	}
	
	public static MyJsonMap newMap() {
		return new MyJsonMap();
	}
}
