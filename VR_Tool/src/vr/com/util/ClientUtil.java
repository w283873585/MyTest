package vr.com.util;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import vr.com.util.ParamsMapUtil.MyMap;

import com.alibaba.fastjson.JSONObject;
import com.vr.show.client.common.VR_HttpClientUtils;


public class ClientUtil {
	private static ExecutorService exec = Executors.newSingleThreadExecutor();
	
	public static void close() {
		exec.shutdown();
	}
	
	public static String clientPost(final Map<String, Object> parmsMap, final String interfaceName, 
			HttpServletRequest request, HttpServletResponse response, String interfaceUrl) throws Exception {
		String result = VR_HttpClientUtils.clientPost(parmsMap, interfaceName, request, response, null, interfaceUrl); //9953B2B5-5302-4FEA-8904-D887C2A393BA
		
		if ("".equals(result)) {
			return "没有匹配的地址！";
		}
		// 如果这个参数信息没有被缓存，则缓存下来
		JSONObject json = JSONObject.parseObject(result);
		if ("0".equals(json.getString("resCode"))) {
			exec.execute(new Runnable() {
				public void run() {
					CacheParamsUtil.putIfAbsent(interfaceName, ((MyMap) parmsMap).getKeysInfo());
				}
			});
		}
		return result;
	}
	
	public static String getParamInfo() {
		return CacheParamsUtil.toJson();
	}
	
	private static class CacheParamsUtil{
		private static final String FILENAME = "service_params.properties";
		private static final Properties propertis = new Properties();
		static {
			try {
				propertis.load(ClientUtil.class.getClassLoader().getResourceAsStream(FILENAME));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		public static String toJson() {
			return JSONObject.toJSONString(propertis);
		}
		
		public static void putIfAbsent(String key, String val) {
			if (propertis.containsKey(key)) {
				return;
			}
			synchronized (CacheParamsUtil.class) {
				if (propertis.containsKey(key)) {
					return;
				}
				propertis.setProperty(key, val);
				// 持久化
				PersistUtil.cacheInterfaceInfo(key, val);
			}
		}
	}
	
	public static void main(String[] args) throws UnsupportedEncodingException, InterruptedException {
		System.out.println(CacheParamsUtil.toJson());
	}
}
