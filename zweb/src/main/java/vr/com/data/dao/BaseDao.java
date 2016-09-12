package vr.com.data.dao;

import java.util.List;

import vr.com.data.Condition;
import vr.com.data.Condition.ConditionType;
import vr.com.data.DataProvider;
import vr.com.data.mongo.MongoCondition;
import vr.com.data.mongo.MongoDbCommand;
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
	
	public void delete(Condition c) {
		getProvider().addCondition(c).invoke(MongoDbCommand.delete);
	}
	
	public void update(T t, Condition c) {
		getProvider().addEntity(t).addCondition(c).invoke(MongoDbCommand.update);
	}
	
	public T selectOne(Condition c, Class<T> t) {
		return getProvider().addCondition(c).invoke(MongoDbCommand.selectOne).getBean(t);
	}
	
	public String select(Condition c) {
		return getProvider().addCondition(c).invoke(MongoDbCommand.select).toString();
	}
	
	public List<T> select(Condition c, Class<T> t) {
		return getProvider().addCondition(c).invoke(MongoDbCommand.select).getList(t);
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
