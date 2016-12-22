package vr.com.command.support;

import vr.com.command.Command;
import vr.com.command.CommandFactory;
import vr.com.command.CommandInfo;
import vr.com.command.CommandReslover;
import vr.com.command.CommandResult;
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
	CommandResult exec(String commandInfo, boolean internal) {
		
		
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
		
		if (!internal)	dao.insert(result);
		
		return result;
	}
	
	public CommandResult exec(String commandInfo) {
		return exec(commandInfo, false);
	}
	
	public static CommandResult execute(String commandInfo) {
		return CommandManager.instance.exec(commandInfo);
	}
}
