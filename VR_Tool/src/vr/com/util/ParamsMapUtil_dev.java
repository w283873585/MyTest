package vr.com.util;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import vr.com.util.rsa.RSA_vrsdev;

/**
 * 一个生成接口参数的工具类
 * @author xnxs
 *
 */
public class ParamsMapUtil_dev {
	public static class MyMap extends HashMap<String, Object> {
		private MyMap() {}
		private static final long serialVersionUID = 1L;
		private static final boolean DEBUG = true;
		private static final String CHARSETUTF8 = "utf-8";
		private List<Map<String, Object>> keysInfo = new ArrayList<Map<String, Object>>();
		public MyMap putA(String key, Object val, boolean needEncrypt, boolean needEncode) throws Exception {
			if (DEBUG) {
				// 保存键的基本信息
				Map<String, Object> keyInfo = new HashMap<String, Object>();
				keyInfo.put("needEncrypt", needEncrypt);
				keyInfo.put("needEncode", needEncode);
				keyInfo.put("value", key);
				keysInfo.add(keyInfo);
			}
			if (val instanceof String) {
				if (needEncode) {
					val = URLEncoder.encode(val.toString(), CHARSETUTF8);
				}
				if (needEncrypt) {
					val = RSA_vrsdev.encrypt(val.toString());
				}
			}
			put(key, val);
			return this;
		}
		public MyMap putA(String key, Object val) throws Exception {
			return putA(key, val, false, false);
		}
		public MyMap putA(String key, Object val, boolean needEncrypt) throws Exception {
			return putA(key, val, needEncrypt, false);
		}
		public String getKeysInfo() {
			return JSONObject.toJSONString(keysInfo);
		}
	}
	public static MyMap putA(String key, Object val) throws Exception {
		return new MyMap().putA(key, val);
	}
	public static MyMap putA(String key, Object val, boolean needEncrypt, boolean needEncode) throws Exception {
		return new MyMap().putA(key, val, needEncrypt, needEncode);
	}
	public static MyMap putA(String key, Object val, boolean needEncrypt) throws Exception {
		return new MyMap().putA(key, val, needEncrypt);
	}
	public static MyMap newMap() {
		return new MyMap();
	}
}