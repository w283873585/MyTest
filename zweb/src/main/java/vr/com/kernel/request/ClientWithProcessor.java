package vr.com.kernel.request;

import vr.com.kernel.request.HttpUtil.MyResponse;

public abstract class ClientWithProcessor implements Client{
	private Client client = new ClientBase();
	
	
	public MyResponse httpRequest(Request request) {
		
		Request req = getRequestProcessor().process(request);
		
		return processResponse(client.httpRequest(req));
	}
	
	// 获取请求加工者
	public abstract RequestProcessor getRequestProcessor();
	
	// 加工响应
	protected MyResponse processResponse(MyResponse res) {
		return res;
	}
}
