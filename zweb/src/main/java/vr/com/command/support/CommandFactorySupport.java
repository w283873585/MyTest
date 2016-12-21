package vr.com.command.support;


import java.util.HashMap;
import java.util.Map;

import vr.com.command.Command;
import vr.com.command.CommandFactory;
import vr.com.command.impl.BaseDbCommand;
import vr.com.command.impl.ClearUserCommand;
import vr.com.command.impl.GetUserCommand;
import vr.com.command.impl.NoneCommand;

public class CommandFactorySupport implements CommandFactory{
	
	private static Map<String, Command> commands = new HashMap<String, Command>();
	static {
		register(new NoneCommand());
		register(new ClearUserCommand());
		register(new BaseDbCommand());
		register(new GetUserCommand());
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
