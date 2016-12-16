package vr.com.commandRe.core.support;

import java.util.HashMap;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandReslover;

public class CommandResloverSupport implements CommandReslover{
	
	public CommandInfoSupport reslove(String commandInfo) {
		
		CommandInfoSupport cInfo = null;
		String commandName = commandInfo;
		int index = commandName.indexOf(" ");
		if (index != -1) {
			commandName = commandInfo.substring(0, index);
		}
		
		cInfo = new CommandInfoSupport(commandName);
		if (index != -1 && index + 1 < commandInfo.length()) {
			String params = commandInfo.substring(index + 1);
			String keyVals[] = params.split("&");
			for (String str : keyVals) {
				String[] keys = str.split("=");
				cInfo.put(keys[0], keys[1]);
			}
		}
		
		return cInfo;
	}
	
	private static class CommandInfoSupport extends HashMap<String, String> implements CommandInfo{

		private static final long serialVersionUID = 5213576145292256136L;
		private String command;
		
		private CommandInfoSupport(String commnadName) {
			this.command = commnadName;
		}

		@Override
		public String getCommandName() {
			return command;
		}
	}
}
