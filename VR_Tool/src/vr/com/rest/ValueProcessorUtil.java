package vr.com.rest;

public class ValueProcessorUtil {
	
	public static String process(String value, String processkeys) {
		String[] keys = processkeys.split(",");
		
		for (String key : keys) {
			ValueProcessor valueProcessor = ValueProcessorFactory.getProcessor(key);
			value = valueProcessor.process(value);
		}
		
		return value;
	}
}
