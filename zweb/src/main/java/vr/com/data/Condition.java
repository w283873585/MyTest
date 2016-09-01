package vr.com.data;

import java.util.Arrays;
import java.util.HashMap;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

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
	public Condition and(String key, Object val) {
		return and(key, val, null);
	}
	
	public Condition and(String key, Object val, ConditionType t) {
		if (t == null)
			this.put(key, val);
		else if (t == ConditionType.oid) 
			this.put(key, new ObjectId(val.toString()));
		else
			this.put(key, build(t.value(), val));
		return this;
	}
	
	public Condition and(Condition c) {
		this.putAll(c);
		return this;
	}
	
	public Condition or(String key, Object val) {
		return or(key, val, null);
	}
	
	public Condition or(String key, Object val, ConditionType t) {
		Condition c = new Condition();
		c.put(OR, Arrays.asList(this, Condition.build(key, val, t)));
		return c;
	}
	
	public Condition or(Condition c) {
		Condition r = new Condition();
		r.put(OR, Arrays.asList(this, c));
		return r;
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
	public enum ConditionType{
		oid,		// 包装为ObjectId
		eq, 		// 等于
		lt, 		// 小于
		gt, 		// 大于
		lte, 		// 小于等于
		gte, 		// 大于等于
		in, 		// 在...里, 修饰数组
		nin,		// 不在...里
		not, 		// 非
		exists, 	// 存在
		type;		// 类型
		
		/**
		Double					1	“double”	 
		String					2	“string”	 
		Object					3	“object”	 
		Array					4	“array”	 
		Binary data				5	“binData”	 
		Undefined				6	“undefined”	Deprecated.
		ObjectId				7	“objectId”	 
		Boolean					8	“bool”	 
		Date					9	“date”	 
		Null					10	“null”	 
		Regular Expression		11	“regex”	 
		DBPointer				12	“dbPointer”	 
		JavaScript				13	“javascript”	 
		Symbol					14	“symbol”	 
		JavaScript (with scope)	15	“javascriptWithScope”	 
		32-bit integer			16	“int”	 
		Timestamp				17	“timestamp”	 
		64-bit integer			18	“long”	 
		Min key					-1	“minKey”	 
		Max key					127	“maxKey”
		*/
		public String value() {
			return "$" + this.name();
		}
	}
}
