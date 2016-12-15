package vr.com.commandRe.core.support;

import vr.com.commandRe.core.Command;
import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;

public abstract class AbstractCommand implements Command{

	public abstract String getName();

	protected boolean check(CommandInfo info) {
		return true;
	}

	public CommandResult invoke(CommandInfo info) {
		if (!check(info)) 
			return CommandResultSupport.error("格式不合法");
		return this.exec(info);
	}
	
	public CommandManager getManager() {
		return CommandManager.instance;
	}
	
	protected abstract CommandResult exec(CommandInfo info);
}
