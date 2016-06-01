package vr.com.request;

import vr.com.request.HttpUtil.MyResponse;

public class ClientBase implements Client{
	
	public String httpRequest(Request request) {
		MyResponse res = HttpUtil.request(request.getUrl(), request.isGet(), request.getData(), true, request.getHeaders());
		return res.toString();
	}

	@Override
	public String getName() {
		return "base";
	}
}
