package vr.com.kernel.processor;

import java.util.Set;

import vr.com.kernel.Factory;

public class ValueProcessorFactory extends Factory<ValueProcessor> {
	
	private static ValueProcessorFactory factory = new ValueProcessorFactory();
	
	static {
		factory.initialize(Processors.values());
	}
	
	public static ValueProcessor getProcessor(String name) {
		ValueProcessor processor = doGetProcessor(name);
		if (processor == null) 
			processor = MixinProcessor.newMixinProcess(name);
		return processor;
	}
	
	static ValueProcessor doGetProcessor(String name) {
		return factory.get(name);
	}
	
	public static Set<String> keySet() {
		return factory.keyset();
	}
}
