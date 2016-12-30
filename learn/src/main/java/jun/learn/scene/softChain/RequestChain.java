package jun.learn.scene.softChain;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

import jun.learn.scene.softChain.annotation.ReqEncryption;
import jun.learn.scene.softChain.annotation.ReqParamRestricts;
import jun.learn.scene.softChain.security.VR_Security_Util;
import jun.learn.scene.softChain.security.VR_Security_Util.BaseResponse;

import java.lang.reflect.Method;

public class RequestChain implements Data{
	
	private Map<String, Object> data;
	private ReqParamRestricts restricts;
	private ReqEncryption reqEncryption;
	
	@SuppressWarnings("unchecked")
	private RequestChain(HttpServletRequest request, Object handler) {
		data = toJsonObject(request.getParameterMap());
		restricts = ((Method) handler).getDeclaredAnnotation(ReqParamRestricts.class);
		reqEncryption = ((Method) handler).getDeclaredAnnotation(ReqEncryption.class);
	}
	
	public Result exec() {
		Result result = new Result();
		
		if (requestEncrypt()) {
			VR_Security_Util.decode(data, result);
		}
		
		checkParams(result);
		
		if (responseEncrypt()) {
			VR_Security_Util.encode(data, result);
		}
		
		return result;
	}

	@Override
	public boolean requestEncrypt() {
		return reqEncryption != null && reqEncryption.requestEncrypt();
	}

	@Override
	public boolean responseEncrypt() {
		return reqEncryption != null && reqEncryption.responseEncrypt();
	}

	@Override
	public boolean checkParams() {
		System.out.println(restricts);
		return false;
	}
	
	private JSONObject toJsonObject(Map<String, Object> parameterMap) {
		JSONObject result = new JSONObject();
		for (String key : parameterMap.keySet()) {
			result.put(key, ((Object[]) parameterMap.get(key))[0]);
		}
		return result;
	}
	
	public static class Result {
		public boolean success;
		private JSONObject attach = new JSONObject();
		
		public boolean isSuccess() {
			return this.success;
		}
		
		public Result attach(String key, Object value) {
			attach.put(key, value);
			return this;
		}
		
		public void doResponse(HttpServletResponse response) {
			PrintWriter out;
			try {
				out = response.getWriter();
				response.setContentType("text/json");
				out.print(attach.toJSONString());
				out.flush();
				out.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
