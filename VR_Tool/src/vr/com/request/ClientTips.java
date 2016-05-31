package vr.com.request;

public abstract class ClientTips implements Client{
	private Client client = new BaseClient();
	
	
	public String httpRequest(Request request) {
		
		Request req = getRequestProcessor().process(request);
		
		return client.httpRequest(req);
	}
	
	// 获取请求加工者
	public abstract RequestProcessor getRequestProcessor();
}
