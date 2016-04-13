package test;

import java.util.HashMap;

public class ParamsMapUtil {
	public static class MyMap<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = 1L;
		@SuppressWarnings("unchecked")
		public MyMap<K, V> putA(K key, V val, boolean needEncrypt, boolean needEncode) throws Exception {
			put(key, val);
			return this;
		}
		public MyMap<K, V> putA(K key, V val) throws Exception {
			return putA(key, val, false, false);
		}
		
		public MyMap<K, V> putA(K key, V val, boolean needEncrypt) throws Exception {
			return putA(key, val, needEncrypt, false);
		}
	}
	public static MyMap<String, Object> putA(String key, Object val, boolean needEncrypt, boolean needEncode) throws Exception {
		return new MyMap<String, Object>().putA(key, val, needEncrypt, needEncode);
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(ParamsMapUtil
			.putA("1", 1, false, true)
			.putA("123", "123213", true, false));
		
		System.out.println(new Integer(5).toString());
		String str = "123";
	}
}
