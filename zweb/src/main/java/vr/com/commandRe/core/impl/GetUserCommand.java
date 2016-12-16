package vr.com.commandRe.core.impl;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.impl.BaseDbCommand.QueryType;
import vr.com.commandRe.core.support.AbstractCommand;
import vr.com.commandRe.core.support.DbCommandVO;

public class GetUserCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "getUser";
	}

	@Override
	protected CommandResult exec(CommandInfo info) {
		DbCommandVO dbCommandVO = DbCommandVO.build("user.getUser", QueryType.selectOne);
		
		String key = info.keySet().iterator().next();
		String value = info.get(key);
		
		dbCommandVO.addParam(key, value);
		CommandResult result = getManager().exec(dbCommandVO);
		
		return result;
	}
}
