package vr.com.commandRe.core.support;

import java.util.HashMap;

import vr.com.commandRe.core.CommandInfo;

public class CommandInfoSupport extends HashMap<String, String> implements CommandInfo{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5213576145292256136L;
	private String command;
	
	public CommandInfoSupport(String commnadName) {
		this.command = commnadName;
	}

	@Override
	public String getCommandName() {
		return command;
	}
}
