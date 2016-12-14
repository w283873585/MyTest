package vr.com.commandRe.core.impl;


import java.util.HashMap;
import java.util.Map;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandFactory;

public class CommandFactoryImpl implements CommandFactory{
	
	private static Map<String, Command> commands = new HashMap<String, Command>();
	static {
		register(null);
	}
	
	private static void register(Command command) {
		commands.put(command.getName(), command);
	}
	
	public Command create(String commandName) {
		if (!commands.containsKey(commandName))
			commandName = "NONE";
		
		return commands.get(commandName);
	}
}
