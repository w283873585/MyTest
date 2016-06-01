package vr.com.request;

import java.util.Set;

import vr.com.kernel.Factory;

public class ClientFactory extends Factory<Client>{
	
	private static ClientFactory factory = new ClientFactory();
	
	static {
		factory.initialize(new ClientBase(),
				new Client_vrdev(), 
				new Client_vrshow());
	}
	
	public static Client getClient(String name) {
		return factory.get(name);
	}
	
	public static Set<String> keySet() {
		return factory.keyset();
	}
}
