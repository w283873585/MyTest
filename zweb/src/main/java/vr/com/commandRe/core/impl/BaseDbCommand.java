package vr.com.commandRe.core.impl;

import org.apache.ibatis.session.SqlSession;

import com.alibaba.fastjson.JSONObject;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.commandRe.core.support.AbstractCommand;
import vr.com.commandRe.core.support.CommandResultSupport;
import vr.com.db.StaticFactory;

public class BaseDbCommand extends AbstractCommand{

	@Override
	public String getName() {
		return "CommonDb";
	}
	
	public CommandResult preHandle(CommandInfo info) {
		String name = info.get("name");
		String type = info.get("type");
		String params = info.get("param");
		
		if (name == null)
			return CommandResultSupport.error("命令名称不能为空");
		if (type == null)
			return CommandResultSupport.error("查询类型不能为空");
		try {
			QueryType.valueOf(type);
		} catch (Exception e) {
			return CommandResultSupport.error("不存在的查询类型");
		}
		if (params == null)
			return CommandResultSupport.error("参数不为空");
		
		return CommandResultSupport.success("验证通过");
	}

	@Override
	protected CommandResult exec(CommandInfo info) {
		CommandResult result = null;
		SqlSession sqlSession = StaticFactory.getSqlSession();
		
		try {
			result = exec(info, sqlSession);
			sqlSession.commit();
		} catch(Exception e) {
			result = CommandResultSupport.error(e.toString());
			sqlSession.rollback();
		} finally {
			sqlSession.close();
		}
		return result;
	}
	
	protected CommandResult exec(CommandInfo info, SqlSession sqlSession) {
		String name = info.get("name");
		String type = info.get("type");
		String params = info.get("param");
		
		QueryType query = QueryType.valueOf(type);
		try {
			Object result = query.invoke(name, JSONObject.parse(params), sqlSession);
			return CommandResultSupport.success(result == null ? "no data" : result.toString());
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
