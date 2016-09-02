package vr.com.data.dao;

import vr.com.data.Condition;
import vr.com.data.DataProvider;
import vr.com.data.mongo.MongoDbCommand;
import vr.com.data.mongo.MongoResource;

public class BaseDao<T> {
	private String collectionName;
	
	protected MongoResource resource = MongoResource.Instance;
	
	private DataProvider provider = MongoResource.Instance.getProvider(getCollectionName());
	
	public BaseDao() {}
	
	public BaseDao(String collectionName) {
		this.collectionName = collectionName;
	}
	
	public void insert(T t) {
		provider.addEntity(t).invoke(MongoDbCommand.insert);
	}
	
	public void delete(Condition c) {
		provider.addCondition(c).invoke(MongoDbCommand.delete);
	}
	
	public void update(T t, Condition c) {
		provider.addEntity(t).addCondition(c).invoke(MongoDbCommand.updateMany);
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
}
