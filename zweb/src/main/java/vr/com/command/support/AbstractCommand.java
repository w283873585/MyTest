package vr.com.command.support;

import vr.com.command.Command;
import vr.com.command.CommandInfo;
import vr.com.command.CommandResult;

public abstract class AbstractCommand implements Command{

	public abstract String getName();

	protected CommandResult preHandle(CommandInfo info) {
		return CommandResultSupport.success("");
	}

	public CommandManager getManager() {
		return CommandManager.instance;
	}
	
	public CommandResult invoke(CommandInfo info) {
		CommandResult checkResult = preHandle(info);
		if (!checkResult.isSuccess()) 
			return checkResult;
		return this.exec(info);
	}
	
	protected abstract CommandResult exec(CommandInfo info);
}
