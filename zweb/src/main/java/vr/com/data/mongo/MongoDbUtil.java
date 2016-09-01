package vr.com.data.mongo;

import java.io.IOException;

import vr.com.data.Condition;

public class MongoDbUtil {
	public void select() throws IOException {
		MongoResource m = new MongoResource();
		m.initialize();
		System.out.println(
				m.getProvider("foo")
				.addCondition(Condition.build("bar", 1224))
				.invoke(MongoDbCommand.selectOne));
		m.close();
	}
	
	public static void main(String[] args) throws IOException {
		new MongoDbUtil().select();
	}
}
