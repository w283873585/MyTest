package vr.com.commandRe.core.impl;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.support.CommandResultSupport;
import vr.com.commandRe.core.support.DbCommand;

public class BaseDbCommand extends DbCommand{

	@Override
	public String getName() {
		return "CommonDb";
	}

	@Override
	protected CommandResult exec(CommandInfo info, SqlSession sqlSession) {
		String name = info.get("name");
		String type = info.get("type");
		String params = info.get("param");
		
		QueryType query = QueryType.valueOf(type);
		try {
			Object result = query.invoke(name, JSONObject.parse(params), sqlSession);
			return CommandResultSupport.success(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
			return CommandResultSupport.error(e.getMessage());
		}
	}
	
	public enum QueryType{
		select {
			public Object invoke(String statement, Object param, SqlSession sqlSession) {
				return sqlSession.selectList(statement, param);
			}
		},
		selectOne {
			public Object invoke(String statement, Object param, SqlSession sqlSession) {
				return sqlSession.selectOne(statement, param);
			}
		},
		update {
			public Object invoke(String statement, Object param, SqlSession sqlSession) {
				return sqlSession.update(statement, param);
			}
		},
		delete {
			public Object invoke(String statement, Object param, SqlSession sqlSession) {
				return sqlSession.delete(statement, param);
			}
		},
		insert {
			public Object invoke(String statement, Object param, SqlSession sqlSession) {
				return sqlSession.insert(statement, param);
			}
		};
		
		public abstract Object invoke(String statement, Object param, SqlSession sqlSession);
	}
}
