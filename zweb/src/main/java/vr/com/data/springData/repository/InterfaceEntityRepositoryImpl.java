package vr.com.data.springData.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.alibaba.fastjson.JSONObject;
import com.mongodb.BasicDBObject;

import vr.com.pojo.InterfaceEntity;

public class InterfaceEntityRepositoryImpl implements InterfaceEntityRepositoryCustom{

	@Autowired
	private MongoOperations operation;

	@Override
	public void updateByUrl(String url, InterfaceEntity t) {
		
		operation.updateFirst(
				Query.query(Criteria.where("url").is(url)), 
				Update.fromDBObject(BasicDBObject.parse(JSONObject.toJSONString(t))), 
				t.getClass());
	}
}
