package vr.com.command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CommandFactory_t {
	private static Map<String, Command> storage = new HashMap<String, Command>();
	private static List<String> keys = new ArrayList<String>();
	static {
		try {
			register(Command.class);
			// register(ClearUserCommand.class);
			// register(GetUserCommand.class);
		} catch (Exception e) {}
	}
	
	public static Command getCommand(String name) {
		return storage.get(name);
	}
	
	public static List<String> getCommandNames() {
		return keys;
	}
	
	private static void register(Class<? extends Command> clazz) throws InstantiationException, IllegalAccessException {
		Command command = clazz.newInstance();
		keys.add(command.getName());
		storage.put(command.getName(), command);
	}
}
