package vr.com.kernel.request;

import vr.com.kernel.request.HttpUtil.MyResponse;

public class ClientBase implements Client{
	
	public MyResponse httpRequest(Request request) {
		MyResponse res = HttpUtil.request(request.getUrl(), request.isGet(), 
				request.getData(), true, request.getHeaders());
		return res;
	}

	@Override
	public String getName() {
		return "base";
	}
}
