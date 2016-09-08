package vr.com.kernel.processor;

import java.util.Set;

import vr.com.kernel.Factory;

public class ValueProcessorFactory extends Factory<ValueProcessor> {
	
	private static ValueProcessorFactory factory = new ValueProcessorFactory();
	
	static {
		factory.initialize(new DevRsaEncodeProcessor(),
				new RsaEncodeProcessor(), 
				new UrlEncodeProcessor(),
				new DesEncodeProcessor(),
				new MD5Processor(),
				new JsonProcessor());
	}
	
	public static ValueProcessor getProcessor(String name) {
		return factory.get(name);
	}
	
	public static Set<String> keySet() {
		return factory.keyset();
	}
}