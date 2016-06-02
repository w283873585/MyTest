package vr.com.util;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;

import vr.com.db.StaticFactory;

public class DbUtil {
	private static final SqlSession sqlSession = StaticFactory.getSqlSession(); 
	
	public static int insertValidInfo(Map<String, Object> map) {
		return sqlSession.insert("user.insertVaildInfo", map);
	}
	
	public static int insertUser(Map<String, Object> map) {
		return sqlSession.insert("user.insertUser", map);
	}
	
	public static int insertSecretInfo(Map<String, Object> map) {
		return sqlSession.insert("user.insertSecretInfo", map);
	}
	
	public static void submit() {
		sqlSession.commit();
		sqlSession.close();
	}
	
	public static void cancel() {
		sqlSession.rollback();
		sqlSession.commit();
	}
}
