package vr.com.command.impl;

import vr.com.command.CommandInfo;
import vr.com.command.CommandResult;
import vr.com.command.support.AbstractCommand;
import vr.com.command.support.CommandResultSupport;

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
