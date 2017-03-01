package vr.com.kernel2.testCase;
/**
 *	表达式执行时的上下文,  
 */
public interface Context {
	
    /**
     *  从上下文池中获取对应数据
     */
    Object get(String key);
	
    /**
     *  往上下文池放入对应数据
     */
	void put(String key, Object value);
	
	
	/**
	 * 当次流程产品的数据收集
	 */
	void setCurrentName(String name);
	 
	void addCurrentParam(String key, Object value);
	 
	/**
	 * 结束当次流程
	 */
	void finishCurrent();
	
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
	
	/**
	 * 查询具体流程信息
	 */
	String getAll();
}