package vr.com.data.dao;

import vr.com.data.Condition;
import vr.com.data.DataProvider;
import vr.com.data.mongo.MongoCondition;
import vr.com.data.mongo.MongoDbCommand;
import vr.com.data.mongo.MongoResource;
import vr.com.pojo.InterfaceEntity;

public class BaseDao<T> {
	private String collectionName;
	
	private DataProvider provider;
	
	public BaseDao(String collectionName) {
		this.collectionName = collectionName;
		provider = MongoResource.Instance.getProvider(getCollectionName());
	}
	
	public BaseDao() {
		provider = MongoResource.Instance.getProvider(getCollectionName());
	}
	
	public void insert(T t) {
		provider.addEntity(t).invoke(MongoDbCommand.insert);
	}
	
	public void delete(Condition c) {
		provider.addCondition(c).invoke(MongoDbCommand.delete);
	}
	
	public void update(T t, Condition c) {
		provider.addEntity(t).addCondition(c).invoke(MongoDbCommand.update);
	}
	
	public String selectOne(Condition c) {
		return provider.addCondition(c).invoke(MongoDbCommand.selectOne);
	}
	
	public String select(Condition c) {
		return provider.addCondition(c).invoke(MongoDbCommand.select);
	}

	protected String getCollectionName() {
		return collectionName;
	}
	
	public static void main(String[] args) {
		InterfaceEntity v = new InterfaceEntity();
		v.setUrl("http://127.0.0.1");
		BaseDao<InterfaceEntity> dao = new BaseDao<InterfaceEntity>("foo");
		System.out.println(dao.select(MongoCondition.noCondtion()));
		dao.update(v, MongoCondition.build("bar", 1224));
	}
}
