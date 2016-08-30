package vr.com.processor;

public class ValueProcessorUtil {
	
	public static Object process(String value, String processkeys) {
		if (processkeys.length() == 0) {
			return value;
		}
		Object result = value;
		String[] keys = processkeys.split(",");
		for (String key : keys) {
			ValueProcessor valueProcessor = ValueProcessorFactory.getProcessor(key);
			result = valueProcessor.process(result.toString());
		}
		
		return result;
	}
}
