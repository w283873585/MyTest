package vr.com.data.dao;

import java.util.List;

import vr.com.data.Condition;
import vr.com.data.Condition.ConditionType;
import vr.com.data.DataProvider;
import vr.com.data.mongo.MongoCondition;
import vr.com.data.mongo.MongoDbCommand;
import vr.com.data.mongo.MongoDbCommand.MongoFindResult;
import vr.com.data.mongo.MongoResource;
import vr.com.pojo.InterfaceEntity;

public class BaseDao<T> {
	// Condition 不对外开放， 用于子类定制查询， 
	// Provider非线程安全类， 由mongoResource动态提供，
	// 每次查询产生一个provider用来组装查询条件。（可将mongoResource实现为工厂，进行优化）
	private String collectionName;
	
	public BaseDao(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public void insert(T t) {
		getProvider().addEntity(t).invoke(MongoDbCommand.insert);
	}
	
	protected void delete(Condition c) {
		getProvider().addCondition(c).invoke(MongoDbCommand.delete);
	}
	
	protected void update(T t, Condition c) {
		getProvider().addEntity(t).addCondition(c).invoke(MongoDbCommand.update);
	}
	
	protected T selectOne(Condition c, Class<T> t) {
		return getProvider().addCondition(c).invoke(MongoDbCommand.selectOne).toBean(t);
	}
	
	protected List<T> select(Condition c, Class<T> t) {
		return getProvider().addCondition(c).invoke(MongoDbCommand.select).toList(t);
	}
	
	protected MongoFindResult select(Condition c) {
		return (MongoFindResult) getProvider().addCondition(c).invoke(MongoDbCommand.select);
	}

	protected DataProvider getProvider() {
		return MongoResource.Instance.getProvider(collectionName);
	}
	
	public static void main(String[] args) {
		InterfaceEntity v = new InterfaceEntity();
		v.setUrl("http://127.0.0.1");
		BaseDao<InterfaceEntity> dao = new BaseDao<InterfaceEntity>("foo");
		System.out.println(dao.select(MongoCondition.noCondtion()));
		System.out.println(dao.select(MongoCondition.build("bar", "b", ConditionType.regex), InterfaceEntity.class));
		System.out.println(new InterfaceEntiyDao().query("123"));
	}
}
