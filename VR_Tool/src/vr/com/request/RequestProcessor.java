package vr.com.request;

public interface RequestProcessor {
	// 加工请求, 返回新的请求对象
	public Request process(Request request);
}
