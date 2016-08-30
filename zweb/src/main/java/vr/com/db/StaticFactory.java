package vr.com.db;

import java.io.IOException;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class StaticFactory {
	private static SqlSessionFactory factory;
	private StaticFactory() {}
	
	static {
		try {
			factory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream("mybatis-config.xml"));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 获取SqlSession 默认非自动提交
	 * @return
	 */
	public static SqlSession getSqlSession(){
		return factory.openSession();
	}
	public static SqlSession getSqlSession(boolean autocommit){
		return factory.openSession(autocommit);
	}
	public static Configuration getConfiguration(){
		return factory.getConfiguration();
	}
	
	public static void main(String[] args) {
		SqlSession sqlSession = getSqlSession();
		Map<?, ?> map = sqlSession.selectOne("user.getUser");
		System.out.println(map);
	}
}
