package vr.com.kernel2.testCase;


public interface Resolver{
	/**
	 * 解析表达式
	 */
	public void resolve(String expression, Context context);
}