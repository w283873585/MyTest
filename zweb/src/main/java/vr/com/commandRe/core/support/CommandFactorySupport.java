package vr.com.commandRe.core.support;


import java.util.HashMap;
import java.util.Map;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandFactory;
import vr.com.commandRe.core.impl.ClearUserCommand;
import vr.com.commandRe.core.impl.BaseDbCommand;
import vr.com.commandRe.core.impl.NoneCommand;

public class CommandFactorySupport implements CommandFactory{
	
	private static Map<String, Command> commands = new HashMap<String, Command>();
	static {
		register(new NoneCommand());
		register(new ClearUserCommand());
		register(new BaseDbCommand());
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
