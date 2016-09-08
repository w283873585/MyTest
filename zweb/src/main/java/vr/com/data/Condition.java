package vr.com.data;

public interface Condition {
	
	public Condition and(Condition c);
	
	public Condition and(String key, Object val);
	
	public Condition and(String key, Object val, ConditionType t);
	
	public Condition or(Condition c);
	
	public Condition or(String key, Object val);
	
	public Condition or(String key, Object val, ConditionType t);
	
	/**
	 * compare operation
	 */
	public enum ConditionType{
		oid,		// 包装为ObjectId
		regex,		// 正则
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
