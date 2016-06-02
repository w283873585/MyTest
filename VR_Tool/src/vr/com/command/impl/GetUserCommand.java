package vr.com.command.impl;

import org.apache.ibatis.session.SqlSession;

import vr.com.command.CommandParamVaild;
import vr.com.command.CommandResult;
import vr.com.util.ParamsMapUtilDes;
import vr.com.util.ParamsMapUtilDes.MyMap;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class GetUserCommand extends DbCommand{

	public GetUserCommand() {
		super(new CommandParamVaild() {
			public boolean check(String paramsInfo) {
				JSONObject param = JSONObject.parseObject(paramsInfo);
				if (param.isEmpty()) {
					return false;
				}
				if (param.containsKey("email") && param.containsKey("mobile")) {
					return false;
				}
				if (!param.containsKey("email") && !param.containsKey("mobile")) {
					return false;
				}
				return true;
			}
		});
	}
	
	@Override
	public CommandResult invoke(SqlSession sqlSession, String paramsInfo) throws Exception {
		JSONObject param = JSONObject.parseObject(paramsInfo);
		MyMap realParam = ParamsMapUtilDes.newMap();
		if (param.containsKey("email")) {
			String email = param.getString("email");
			realParam.putA("user_email", email, true);
		} else {
			String mobile = param.getString("mobile");
			realParam.putA("user_mobile", mobile, true);
		}
		return CommandResult.success(JSON.toJSONString(sqlSession.selectOne("user.getUser", realParam)));
	}

	@Override
	public String getName() {
		return "getUser";
	}
}
