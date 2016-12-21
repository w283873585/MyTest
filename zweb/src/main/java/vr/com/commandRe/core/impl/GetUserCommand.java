package vr.com.commandRe.core.impl;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.impl.BaseDbCommand.QueryType;
import vr.com.commandRe.core.support.AbstractCommand;
import vr.com.commandRe.core.support.DbCommandVO;
import vr.com.util.text.StringProcessors;

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
		if (!"user_id".equals(key))
			value = StringProcessors.des_encrypt.process(value);
		
		CommandResult result = dbCommandVO.addParam(key, value).execute(getManager());;
		return result;
	}
}
