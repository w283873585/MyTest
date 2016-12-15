package vr.com.commandRe.core.impl;

import java.util.UUID;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.impl.BaseDbCommand.QueryType;
import vr.com.commandRe.core.support.AbstractCommand;

public class ClearUserCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "clearUser";
	}

	@Override
	protected CommandResult exec(CommandInfo info) {
		
		DbCommandVO dbCommandVO = DbCommandVO.build("user.delUser", QueryType.update);
		
		String key = info.contains("email") ? "email" : "mobile";
		String newKey = "new" + key;
		String value = info.get(key);
		String newValue = info.contains(newKey) ? info.get(newKey) : UUID.randomUUID().toString();
		
		dbCommandVO.addParam(key, value);
		dbCommandVO.addParam(newKey, newValue);
		CommandResult result = getManager().exec(dbCommandVO);
		
		if (result.isSuccess()) {
			DbCommandVO rollbackCommand = DbCommandVO.build("user.delUser", QueryType.update);
			rollbackCommand.addParam(key, newValue);
			rollbackCommand.addParam(newKey, value);
			result.setRollbackCommand(rollbackCommand.toString());
		}
		
		return result;
	}
}
