package jun.learn.scene.softChain.kernel;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONObject;

public class RequestManager{
	
	public static ReqResult invoke(HttpServletRequest request, Method handle) {
		
		final ReqResult result = new ReqResult();
		MetaData metaData = new MetaData(handle);
		ReqData reqData = new ReqData(getParamJson(request));
		for (Interceptor invoker : Interceptors.values()) {
			invoker.intercept(metaData, reqData, result);
		}
		
		if (!result.hasCommit()) {
			result.success();
			reqData.each(new Carrier() {
				@Override
				public void put(String key, Object val) {
					result.attach(key, val);
				}
			});
		}
		
		return result;
	}
	
	
	@SuppressWarnings("unchecked")
	private static JSONObject getParamJson(HttpServletRequest request) {
		Map<String, Object> parameterMap = request.getParameterMap();
		JSONObject result = new JSONObject();
		for (String key : parameterMap.keySet()) {
			result.put(key, ((Object[]) parameterMap.get(key))[0]);
		}
		return result;
	}
}
