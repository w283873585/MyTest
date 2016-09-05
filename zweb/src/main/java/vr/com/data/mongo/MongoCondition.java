package vr.com.data.mongo;

import java.util.Arrays;
import java.util.HashMap;

import org.bson.types.ObjectId;

import vr.com.data.Condition;

public class MongoCondition extends HashMap<String, Object> implements Condition{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4212670712276724405L;
	
	private static final String OR = "$or";

	private MongoCondition(){}
	
	/**
	 * 	building
	 */
	public static MongoCondition build(String key, Object val, ConditionType t) {
		MongoCondition c = new MongoCondition();
		c.and(key, val, t);
		return c;
	}
	
	public static MongoCondition build(String key, Object val) {
		return build(key, val, null);
	}
	
	public static MongoCondition build(String expression) {
		MongoCondition c = new MongoCondition();
		return c;
	}
	
	public static MongoCondition noCondtion() {
		return new MongoCondition();
	}
	
	/**
	 *  logic operation
	 */
	public MongoCondition and(String key, Object val) {
		return and(key, val, null);
	}
	
	public MongoCondition and(String key, Object val, ConditionType t) {
		if (t == null)
			this.put(key, val);
		else if (t == ConditionType.oid) 
			this.put(key, new ObjectId(val.toString()));
		else
			this.put(key, build(t.value(), val));
		return this;
	}
	
	public MongoCondition and(Condition c) {
		this.putAll((MongoCondition) c);
		return this;
	}
	
	public MongoCondition or(String key, Object val) {
		return or(key, val, null);
	}
	
	public MongoCondition or(String key, Object val, ConditionType t) {
		MongoCondition c = new MongoCondition();
		c.put(OR, Arrays.asList(this, MongoCondition.build(key, val, t)));
		return c;
	}
	
	public MongoCondition or(Condition c) {
		MongoCondition r = new MongoCondition();
		r.put(OR, Arrays.asList(this, c));
		return r;
	}
}
