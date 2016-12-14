package vr.com.commandRe.core.impl;

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
			return null;
		
		
		return null;
	}
	
	protected abstract CommandResult exec(CommandInfo info);

	public boolean canRollback() {
		return false;
	}

	public CommandResult rollback(CommandInfo info) {
		throw new RuntimeException("no support rollback");
	}
}
