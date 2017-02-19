package vr.com.kernel2.httpAPI;

import java.util.Map;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSONObject;

import vr.com.kernel.request.HttpUtil.MyResponse;

public class HttpApIResultAdapter implements HttpAPIResult{

	public HttpApIResultAdapter(MyResponse response) {
		this.response = response;
	}
	
	private MyResponse response;
	private JSONObject body = null;
	
	@Override
	public int getCode() {
		return response.code;
	}

	@Override
	public String getAll() {
		return response.getAll();
	}

	@Override
	public String getBody() {
		return response.getString();
	}

	@Override
	public Map<String, Object> getHeader() {
		return JSONObject.parseObject(response.getHeaderString());
	}

	@Override
	public Object get(String key) {
		if (body == null)
			body = JSONObject.parseObject(getBody());
		return body.get(key);
	}

	@Override
	public void foreach(BiConsumer<String, Object> consumer) {
		if (body == null)
			body = JSONObject.parseObject(getBody());
		
		body.forEach(consumer);
	}
}
