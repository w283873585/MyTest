package vr.com.command.impl;

import org.apache.ibatis.session.SqlSession;

import vr.com.command.Command;
import vr.com.command.CommandParamVaild;
import vr.com.command.CommandResult;
import vr.com.db.StaticFactory;
import vr.com.util.PersistUtil;


// 构造器注册
public abstract class DbCommand implements Command{
	private CommandParamVaild vaildUtil = new CommandParamVaild() {
		public boolean check(String paramsInfo) {
			return true;
		}
	};
	public DbCommand() {}
	public DbCommand(CommandParamVaild util) {
		this.vaildUtil = util;
	}
	
	@Override
	public CommandResult exec(String paramsInfo) {
		if (!vaildUtil.check(paramsInfo)) {
			return CommandResult.error("输入的参数错误");
		}
		
		CommandResult result = null;
		SqlSession sqlSession = StaticFactory.getSqlSession();
		
		try {
			result = invoke(sqlSession, paramsInfo);
			if (result.isSuccess()) {
				PersistUtil.cacheCommand(getName(), result.toString());
			}
			sqlSession.commit();
		} catch(Exception e) {
			result = CommandResult.error(e.toString());
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		
		return result;
	}
	
	public abstract CommandResult invoke(SqlSession sqlSession, String paramsInfo) throws Exception;
}
