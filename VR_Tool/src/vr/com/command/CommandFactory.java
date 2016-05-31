package vr.com.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

public class CommandFactory {
	private static Map<String, Command> storage = new HashMap<String, Command>();
	private static List<String> keys = new ArrayList<String>();
	private static final String FILENAME = "command.properties";
	static {
		try {
			// TODO 错误处理	命令回滚 命令记录
			Properties propertis = new Properties();
			propertis.load(CommandFactory.class.getClassLoader().getResourceAsStream(FILENAME));
			for (Object key : propertis.keySet()) {
				String strKey = (String) key;
				keys.add(strKey);
				@SuppressWarnings("unchecked")
				Class<? extends Command> clazz = (Class<? extends Command>) Class.forName(propertis.getProperty(strKey));
				register(strKey, clazz.newInstance());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static Command getCommand(String name) {
		return storage.get(name);
	}
	
	public static List<String> getCommandNames() {
		return keys;
	}
	
	private static void register(String name, Command command) {
		storage.put(name, command);
	}
}
