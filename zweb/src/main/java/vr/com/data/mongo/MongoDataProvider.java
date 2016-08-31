package vr.com.data.mongo;

import java.util.ArrayList;
import java.util.List;

import vr.com.data.Command;
import vr.com.data.Condition;
import vr.com.data.DataProvider;
import vr.com.data.Filter;

public class MongoDataProvider implements DataProvider{

	private List<Condition> conditions = new ArrayList<Condition>();
	
	private List<Filter> filters = new ArrayList<Filter>();
	
	private List<String> entitys = new ArrayList<String>();
	
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
	public DataProvider addEntity(String entity) {
		entitys.add(entity);
		return this;
	}
	
	@Override
	public String invoke(Command command) {
		return command.exec(this);
	}
}
