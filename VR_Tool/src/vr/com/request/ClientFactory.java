package vr.com.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientFactory {
	private static Map<String, Client> clients = new HashMap<String, Client>();
	
	static {
		Client clientBase = new ClientBase();
		Client client_dev = new Client_vrdev();
		Client client_vrshow = new Client_vrshow();
		clients.put(clientBase.getName(), clientBase);
		clients.put(client_dev.getName(), client_dev);
		clients.put(client_vrshow.getName(), client_vrshow);
	}
	
	public static Client getClient(String name) {
		return null;
	}
	
	public static Set<String> keySet() {
		return clients.keySet();
	}
}
