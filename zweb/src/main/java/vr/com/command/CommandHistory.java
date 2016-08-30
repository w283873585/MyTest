package vr.com.command;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommandHistory {
	private static final String FILENAME = "commandCache.properties";
	
	public static List<Map<String, String>> getCommands() {
		try {
			List<Map<String, String>> result = new ArrayList<Map<String, String>>();
			Properties propertis = new Properties();
			propertis.load(CommandHistory.class.getClassLoader().getResourceAsStream(FILENAME));
			for (Object key : propertis.keySet()) {
				Map<String, String> map = new HashMap<String, String>();
				String strKey = (String) key;
				String val = propertis.getProperty(strKey);
				map.put(strKey, val);
				result.add(map);
			}
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
