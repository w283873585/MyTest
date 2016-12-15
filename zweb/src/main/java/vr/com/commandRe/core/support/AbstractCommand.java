package vr.com.commandRe.core.support;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;

public abstract class AbstractCommand implements Command{

	public abstract String getName();

	protected CommandResult check(CommandInfo info) {
		return CommandResultSupport.success("");
	}

	public CommandResult invoke(CommandInfo info) {
		CommandResult checkResult = check(info);
		if (!checkResult.isSuccess()) 
			return checkResult;
		return this.exec(info);
	}
	
	public CommandManager getManager() {
		return CommandManager.instance;
	}
	
	protected abstract CommandResult exec(CommandInfo info);
}
