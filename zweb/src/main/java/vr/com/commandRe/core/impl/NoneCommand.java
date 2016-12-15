package vr.com.commandRe.core.impl;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.support.AbstractCommand;
import vr.com.commandRe.core.support.CommandResultSupport;

public class NoneCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "NONE";
	}

	@Override
	protected CommandResult exec(CommandInfo info) {
		return CommandResultSupport.error("不支持的命令");
	}
	
}
