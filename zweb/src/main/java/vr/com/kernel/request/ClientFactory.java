package vr.com.kernel.request;

import java.util.Set;

import vr.com.kernel.Factory;

public class ClientFactory extends Factory<Client>{
	
	private static ClientFactory factory = new ClientFactory();
	
	static {
		factory.initialize(new Client_vrshow(),
				new Client_vrdev(), 
				new ClientBase());
	}
	
	public static Client getClient(String name) {
		return factory.get(name);
	}
	
	public static Set<String> keySet() {
		return factory.keyset();
	}
}
