package vr.com.commandRe.core.support;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandFactory;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandReslover;
import vr.com.commandRe.core.CommandResult;
import vr.com.data.dao.BaseDao;

public enum CommandManager {
	
	instance;
	
	/**	 commandFactory  */
	private CommandFactory commandFactory = new CommandFactorySupport();
	
	/**	 commandReslover  */
	private CommandReslover	commandReslover = new CommandResloverSupport();
	
	/**  mongodb util */
	private BaseDao<CommandResult> dao = new BaseDao<CommandResult>("CommandResult"); 
	
	/**
	 *	parse the string to CommandInfo object,
	 *	then invoke the command
	 *	finally return result
	 */
	public CommandResult exec(String commandInfo) {
		
		
		/**
		 * CommandReslover 解析命令字符串, 返回CommandInfo(一个标识了commandName的map, map中存储的是参数信息)
		 * 
		 * CommandFactory 生成对应的Command, (command作为无状态的策略类, 只具有消费CommandInfo的能力.)
		 * 
		 * Command根据CommandInfo参数执行, 并返回结果CommandResult.
		 * 
		 * CommandResult具有设置回滚命令和记录原始命令的功能.
		 */
		
		
		CommandInfo cInfo = commandReslover.reslove(commandInfo);

		Command command = commandFactory.create(cInfo.getCommandName());

		CommandResult result = command.invoke(cInfo);
		
		result.setOriginCommand(commandInfo);
		
		dao.insert(result);
		
		return result;
	}
	
	public static CommandResult execute(String commandInfo) {
		return CommandManager.instance.exec(commandInfo);
	}
}
