package vr.com.kernel.processor;

import java.util.Set;

import vr.com.kernel.Factory;

public class ValueProcessorFactory extends Factory<ValueProcessor> {
	
	private static ValueProcessorFactory factory = new ValueProcessorFactory();
	
	static {
		factory.initialize(Processors.values());
	}
	
	public static ValueProcessor getProcessor(String name) {
		ValueProcessor processor = factory.get(name);
		if (processor == null) 
			processor = MixinProcessor.newMixinProcess(name);
		return processor;
	}
	
	public static Set<String> keySet() {
		return factory.keyset();
	}
}
