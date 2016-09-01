package vr.com.data;

import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import org.bson.conversions.Bson;

public class Condition extends HashMap<String, Object>{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4212670712276724405L;
	
	private static final String OR = "$or";

	private Condition(){}
	
	/**
	 * 	building
	 */
	public static Condition build(String key, Object val, ConditionType t) {
		Condition c = new Condition();
		c.and(key, val, t);
		return c;
	}
	
	public static Condition build(String key, Object val) {
		return build(key, val, null);
	}
	
	public static Condition build(String expression) {
		Condition c = new Condition();
		return c;
	}
	
	/**
	 *  logic operation
	 */
	public Condition and(String key, Object val, ConditionType t) {
		this.put(key, t == null ? val : build(t.value(), val));
		return this;
	}
	
	public Condition and(Condition c) {
		this.putAll(c);
		return this;
	}
	
	public Condition or(String key, Object val, ConditionType t) {
		Condition c = new Condition();
		c.put(OR, Arrays.asList(this, Condition.build(key, val, t)));
		return c;
	}
	
	/**
	 * ToBson
	 */
	public Bson toBson() {
		return new Document(this);
	}
	
	
	/**
	 * compare operation
	 */
	enum ConditionType{
		eq, lt, gt, lte, gte, in, not;
		
		public String value() {
			return "$" + this.name();
		}
	}
}
