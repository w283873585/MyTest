package vr.com.data.dao;

import java.util.ArrayList;
import java.util.List;

import vr.com.data.Condition.ConditionType;
import vr.com.data.mongo.MongoCondition;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;

public class InterfaceEntiyDao extends BaseDao<InterfaceEntity>{
	
	public InterfaceEntiyDao() {
		super("InterfaceEntity");
	}

	public List<InterfaceEntity> query(String keyword) {
		return select(MongoCondition.build("name", keyword, ConditionType.regex), InterfaceEntity.class);
	}
	
	public boolean existInterface(String url) {
		long count = select(MongoCondition.build("url", url)).count();
		return count != 0;
	}
	
	public void updateByUrl(String url, InterfaceEntity entity) {
		update(entity, MongoCondition.build("url", url));
	}
	
	public static void main(String[] args) {
		InterfaceEntity entity = new InterfaceEntity();
		entity.setName("红包注册");
		entity.setDesc("手机号码注册并送红包口令服务接口");
		entity.setUrl("/api/activity/user/mobile/register/service");
		List<InterfaceParam> params = new ArrayList<InterfaceParam>();
		
		InterfaceParam param = new InterfaceParam();
		param.setKey("mobile");
		param.setConstraint("rsa");
		param.setDesc("手机号");
		
		InterfaceParam param1 = new InterfaceParam();
		param1.setKey("loginPassword");
		param1.setConstraint("md5,rsa");
		param1.setDesc("登陆密码");
		
		
		InterfaceParam param2 = new InterfaceParam();
		param2.setKey("smsCode");
		param2.setConstraint("rsa");
		param2.setDesc("注册码");
		
		params.add(param);
		params.add(param1);
		params.add(param2);
		
		entity.setParams(params);
		
		List<InterfaceParam> results = new ArrayList<InterfaceParam>();
		
		InterfaceParam param3 = new InterfaceParam();
		param3.setKey("userId");
		param3.setConstraint("rsa");
		param3.setDesc("用户id");
		
		InterfaceParam param4 = new InterfaceParam();
		param4.setKey("redPacketsPassword");
		param4.setConstraint("rsa");
		param4.setDesc("红包指令");
		
		results.add(param3);
		results.add(param4);
		entity.setResults(results);
		
		new InterfaceEntiyDao().insert(entity);
	}
}
