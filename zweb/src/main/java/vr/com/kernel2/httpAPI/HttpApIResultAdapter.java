package vr.com.kernel2.httpAPI;

import java.util.Map;
import java.util.function.BiConsumer;

import com.alibaba.fastjson.JSONObject;

import vr.com.kernel.request.HttpUtil.MyResponse;
import vr.com.util.ExceptionUtil;

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
		Object result = null;
		try {
			if (body == null)
				body = JSONObject.parseObject(getBody());
			result = body.get(key);
		} catch (Exception e) {
			ExceptionUtil.throwRuntimeException("接口返回数据不为json：" + getBody());
		}
		return result;
	}

	@Override
	public void foreach(BiConsumer<String, Object> consumer) {
        try {
        	if (body == null)
        		body = JSONObject.parseObject(getBody());
        	body.forEach(consumer);
        } catch (Exception e) {
            ExceptionUtil.throwRuntimeException("接口返回数据不为json：" + getCode());
        }
	}
}
