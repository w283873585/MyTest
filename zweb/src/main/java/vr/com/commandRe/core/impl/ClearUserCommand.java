package vr.com.commandRe.core.impl;

import java.util.UUID;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.impl.BaseDbCommand.QueryType;
import vr.com.commandRe.core.support.AbstractCommand;
import vr.com.commandRe.core.support.DbCommandVO;

public class ClearUserCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "clearUser";
	}

	@Override
	protected CommandResult exec(CommandInfo info) {

		String key = info.containsKey("email") ? "email" : "mobile";
		String newKey = "new" + key;
		String value = info.get(key);
		String newValue = info.containsKey(newKey) ? info.get(newKey) : UUID.randomUUID().toString();
		
		DbCommandVO dbCommandVO = DbCommandVO.build("user.delUser", QueryType.update);
		CommandResult result = dbCommandVO.addParam(key, value, newKey, newValue).execute(getManager());
		
		if (result.isSuccess()) {
			result.setRollbackCommand(dbCommandVO.addParam(key, newValue, newKey, value).toString());
		}
		
		return result;
	}
}
