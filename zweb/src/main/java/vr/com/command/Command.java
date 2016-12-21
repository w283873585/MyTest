package vr.com.command;

import vr.com.command.support.CommandManager;

public interface Command {

	String getName();
	
	CommandResult invoke(CommandInfo info);
	
	CommandManager getManager();
	
	/**
	 * for rollback
	boolean canRollback();
	
	CommandResult rollback(CommandInfo info);
	
	*/
	/**
	 * 依赖流程, 依赖数据
	 */
}
