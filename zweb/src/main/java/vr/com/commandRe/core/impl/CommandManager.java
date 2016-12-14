package vr.com.commandRe.core.impl;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandFactory;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandReslover;
import vr.com.commandRe.core.CommandResult;

public class CommandManager {
	
	/**	 commandFactory  */
	private CommandFactory commandFactory = new CommandFactoryImpl();
	
	/**	 commandReslover  */
	private CommandReslover	commandReslover = new CommandResloverImpl();
	
	
	/**
	 *	parse the string to CommandInfo object,
	 *	then invoke the command
	 *	finally return result
	 */
	public void exec(String commandInfo) {
		
		CommandInfo cInfo = commandReslover.reslove(commandInfo);

		Command command = commandFactory.create(cInfo.getCommandName());
		
		CommandResult result = command.invoke(cInfo);
		
		System.out.println(result);
	}
}
