package vr.com.data.dao;

import java.util.List;

import vr.com.data.mongo.MongoCondition;
import vr.com.pojo.InterfaceEntity;

public class InterfaceEntiyDao extends BaseDao<InterfaceEntity>{
	
	public InterfaceEntiyDao() {
		super("InterfaceEntity");
	}

	public List<InterfaceEntity> query(String keyword) {
		return select(MongoCondition.build("name", keyword), InterfaceEntity.class);
	}
}
