package vr.com.kernel;

import java.util.Map;

import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.Request;

public class HttpClient {
	
	public static String doRequest(String url, Map<String, Object> params) {
		Client client = ClientFactory.getClient("vrshow");
		
		Request req = new Request(false, url, params);
		
		return client.httpRequest(req).toString();
	}
}
