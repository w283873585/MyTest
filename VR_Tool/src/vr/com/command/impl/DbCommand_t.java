package vr.com.command.impl;

import org.apache.ibatis.session.SqlSession;

import vr.com.db.StaticFactory;

abstract class DbCommand_t{
	public DbCommand_t() {}
	
	public String exec(String paramsInfo) throws Exception {
		if (!check(paramsInfo)) {
			return "输入的参数错误";
		}
		String result = "";
		SqlSession sqlSession = StaticFactory.getSqlSession();
		try {
			result = invoke(sqlSession, paramsInfo);
			sqlSession.commit();
		} catch(Exception e) {
			sqlSession.rollback();
			throw e;
		} finally {
			sqlSession.close();
		}
		return result;
	}
	
	// 参数验证
	protected boolean check(String paramsInfo) {
		return true;
	}
	
	public abstract String invoke(SqlSession sqlSession, String paramsInfo) throws Exception;
}
