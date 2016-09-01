package vr.com.data.mongo;

import java.io.IOException;

import vr.com.data.Condition;
import vr.com.data.Condition.ConditionType;
import vr.com.pojo.VRInterface;

public class MongoDbUtil {
	public void select() throws IOException {
		MongoResource m = new MongoResource();
		m.initialize();
		System.out.println(
				m.getProvider("foo")
				.addCondition(Condition.build("_id", "objectId", ConditionType.type))
				.invoke(MongoDbCommand.selectOne));
		m.close();
	}
	
	public void insert(VRInterface vo) throws IOException {
		MongoResource m = new MongoResource();
		m.initialize();
		m.getProvider("foo")
			.addEntity(vo)
			.invoke(MongoDbCommand.insert);
		m.close();
	}
	
	public static void main(String[] args) throws IOException {
		VRInterface vo = new VRInterface();
		vo.setUrl("www.baidu.com");
		new MongoDbUtil().insert(vo);
		
		new MongoDbUtil().select();
	}
}
