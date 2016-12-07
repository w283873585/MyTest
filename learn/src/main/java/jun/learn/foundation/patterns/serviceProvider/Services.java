package jun.learn.foundation.patterns.serviceProvider;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Services {
	private Services(){}	// Prevents instantiation
	
	// Maps services names to services 
	private static final Map<String,Provider> providers = 
			new ConcurrentHashMap<String,Provider>();
	
	public static final String DEFAULT_PROVIDER_NAME = "<def>";
	
	public static void registerDefaultProvider(Provider p){
		registerDefaultProvider(DEFAULT_PROVIDER_NAME, p);
	}
	
	public static void registerDefaultProvider(String name,Provider p){
		providers.put(name, p);
	}
	
	public static Service newInstance(String name){
		Provider p = providers.get(name);
		if(p==null){
			throw new IllegalArgumentException("");
		}
		return p.newService();
	}
}
