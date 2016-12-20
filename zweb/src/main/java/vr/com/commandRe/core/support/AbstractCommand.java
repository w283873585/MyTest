package vr.com.commandRe.core.support;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;

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
