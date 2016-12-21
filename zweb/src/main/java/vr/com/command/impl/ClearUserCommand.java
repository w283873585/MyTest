package vr.com.command.impl;

import static vr.com.util.text.StringProcessors.des_encrypt;

import java.util.UUID;

import vr.com.command.CommandInfo;
import vr.com.command.CommandResult;
import vr.com.command.impl.BaseDbCommand.QueryType;
import vr.com.command.support.AbstractCommand;
import vr.com.command.support.DbCommandVO;


public class ClearUserCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "clearUser";
	}

	@Override
	protected CommandResult exec(CommandInfo info) {

		String key = info.containsKey("email") ? "email" : "mobile";
		String newKey = "new" + key;
		String value = des_encrypt.process(info.get(key));
		String newValue = des_encrypt.process(info.containsKey(newKey) ? info.get(newKey) : UUID.randomUUID().toString());
		
		DbCommandVO dbCommandVO = DbCommandVO.build("user.delUser", QueryType.update);
		CommandResult result = dbCommandVO.addParam(key, value, newKey, newValue).execute(getManager());
		
		if (result.isSuccess()) {
			result.setRollbackCommand(dbCommandVO.addParam(key, newValue, newKey, value).toString());
		}
		
		return result;
	}
}
