package vr.com.data.mongo;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;

import vr.com.data.Command;
import vr.com.data.Condition;
import vr.com.data.DataProvider;
import vr.com.data.Filter;

public class MongoDataProvider implements DataProvider{

	private MongoCollection<Document> db = null;
	
	private List<Condition> conditions = new ArrayList<Condition>();
	
	private List<Filter> filters = new ArrayList<Filter>();
	
	private List<Object> entitys = new ArrayList<Object>();
	
	public MongoDataProvider(MongoCollection<Document> db) {
		this.db = db;
	}
	
	@Override
	public DataProvider addCondition(Condition condition) {
		conditions.add(condition);
		return this;
	}

	@Override
	public DataProvider addFilter(Filter filter) {
		filters.add(filter);
		return this;
	}
	
	@Override
	public DataProvider addEntity(Object entity) {
		entitys.add(entity);
		return this;
	}
	
	@Override
	public String invoke(Command command) {
		String result = command.exec(this);
		entitys.clear();
		filters.clear();
		conditions.clear();
		return result;
	}
	
	public MongoCollection<Document> getDb() {
		return db;
	}

	public Condition getCondition() {
		Condition c = null;
		for (Condition d : conditions) {
			c = c == null ? d : c.and(d);
		}
		return c;
	}
	
	public List<Filter> getFilters() {
		return filters;
	}

	public List<Object> getEntitys() {
		return entitys;
	}
}
