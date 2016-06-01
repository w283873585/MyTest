package vr.com.request;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClientFactory {
	private static Map<String, Client> clients = new HashMap<String, Client>();
	
	static {
		register(new ClientBase());
		register(new Client_vrdev());
		register(new Client_vrshow());
	}
	
	private static void register(Client client) {
		clients.put(client.getName(), client);
	}
	
	public static Client getClient(String name) {
		return null;
	}
	
	public static Set<String> keySet() {
		return clients.keySet();
	}
}
