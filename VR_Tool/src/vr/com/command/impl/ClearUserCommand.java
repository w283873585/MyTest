package vr.com.command.impl;

import java.util.UUID;

import org.apache.ibatis.session.SqlSession;

import vr.com.command.CommandParamVaild;
import vr.com.command.CommandResult;
import vr.com.command.Rollbackable;
import vr.com.util.JsonUtil;
import vr.com.util.JsonUtil.MyJson;
import vr.com.util.ParamsMapUtilDes;
import vr.com.util.ParamsMapUtilDes.MyMap;

import com.alibaba.fastjson.JSONObject;

public class ClearUserCommand extends DbCommand implements Rollbackable{

	public ClearUserCommand() {
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
		MyJson result = null;
		if (param.containsKey("email")) {
			String email = param.getString("email");
			realParam.putA("user_email", email, true);
			realParam.putA("newEmail", UUID.randomUUID().toString(), true);
			result = JsonUtil.putA("email", email);
		} else {
			String mobile = param.getString("mobile");
			realParam.putA("user_mobile", mobile, true);
			realParam.putA("newMobile", UUID.randomUUID().toString(), true);
			result = JsonUtil.putA("mobile", mobile);
		}
		long changes = sqlSession.update("user.delUser", realParam);
		System.out.println(changes);
		return CommandResult.success(result
				.putA("msg", changes == 1 ? "清除成功" : "清除失败")
				.putA("user_id", realParam.get("id"))
				.toJSONString());
	}
	
	@Override
	public CommandResult rollback(String paramsInfo) {
		return new RestoreUserCommand().exec(paramsInfo);
	}

	private static class RestoreUserCommand extends DbCommand implements Rollbackable{

		public RestoreUserCommand() {
			super(new CommandParamVaild() {
				public boolean check(String paramsInfo) {
					try {
						JSONObject param = JSONObject.parseObject(paramsInfo).getJSONObject("data");
						if (!param.containsKey("user_id")) {
							return false;
						}
						if (param.containsKey("email") && param.containsKey("mobile")) {
							return false;
						}
						if (!param.containsKey("email") && !param.containsKey("mobile")) {
							return false;
						}
					} catch(Exception e) {
						return false;
					}
					return true;
				}
			});
		}
		
		@Override
		public String getName() {
			return "restoreUser";
		}

		@Override
		public CommandResult invoke(SqlSession sqlSession, String paramsInfo)
				throws Exception {
			MyJson result = null;
			JSONObject param = JSONObject.parseObject(paramsInfo).getJSONObject("data");
			MyMap realParam = ParamsMapUtilDes.putA("user_id", param.getString("user_id"));
			if (param.containsKey("email")) {
				String email = param.getString("email");
				realParam.putA("newEmail", email, true);
				result = JsonUtil.putA("user_email", email);
			} else {
				String mobile = param.getString("mobile");
				realParam.putA("newMobile", mobile, true);
				result = JsonUtil.putA("user_mobile", mobile);
			}
			int changes = sqlSession.update("user.delUser", realParam);
			return changes == 1 ? CommandResult.success(result.toJSONString()) : CommandResult.error("恢复失败");
		}

		@Override
		public CommandResult rollback(String paramsInfo) {
			return new ClearUserCommand().exec(paramsInfo);
		}
	}
	
	
	@Override
	public String getName() {
		return "clearUser";
	}
}
