package vr.com.commandRe.core.support;

import org.apache.ibatis.session.SqlSession;

import vr.com.commandRe.core.CommandInfo;
import vr.com.commandRe.core.CommandResult;
import vr.com.db.StaticFactory;

public abstract class DbCommand extends AbstractCommand{

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
	
	public abstract String getName();
	
	protected abstract CommandResult exec(CommandInfo info, SqlSession sqlSession);
}
