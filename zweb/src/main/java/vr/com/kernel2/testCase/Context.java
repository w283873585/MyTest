package vr.com.kernel2.testCase;
/**
 *	表达式执行时的上下文,  
 */
public interface Context {
	Object get(String key);
	
	void put(String key, Object value);
	
	/**
	 * 是否提交
	 */
	boolean hasCommitted();
	
	/**
	 * 提交响应 信息
	 */
	void commit(String msg);
	
	/**
	 * 最终的响应信息
	 */
	String getMsg();
}