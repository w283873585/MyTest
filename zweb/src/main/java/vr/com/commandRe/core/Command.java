package vr.com.commandRe.core;

public interface Command {

	String getName();
	
	CommandResult invoke(CommandInfo info);
	
	/**
	 * for rollback
	boolean canRollback();
	
	CommandResult rollback(CommandInfo info);
	
	*/
	/**
	 * 依赖流程, 依赖数据
	 */
}
