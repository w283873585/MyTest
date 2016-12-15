package vr.com.commandRe.core.support;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandFactory;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandReslover;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.impl.DbCommandVO;
import vr.com.data.dao.BaseDao;

public enum CommandManager {
	
	instance;
	
	/**	 commandFactory  */
	private CommandFactory commandFactory = new CommandFactorySupport();
	
	/**	 commandReslover  */
	private CommandReslover	commandReslover = new CommandResloverSupport();
	
	/** mongo db util */
	private BaseDao<CommandResult> dao = new BaseDao<CommandResult>("commandResult"); 
	
	/**
	 *	parse the string to CommandInfo object,
	 *	then invoke the command
	 *	finally return result
	 */
	public CommandResult exec(String commandInfo) {
		
		CommandInfo cInfo = commandReslover.reslove(commandInfo);

		Command command = commandFactory.create(cInfo.getCommandName());

		CommandResult result = command.invoke(cInfo);
		
		dao.insert(result);
		
		return result;
	}
	
	public CommandResult exec(DbCommandVO dbCommand) {
		return exec(dbCommand.toString());
	}
}
