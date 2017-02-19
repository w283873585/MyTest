package vr.com.kernel2.testCase;

import vr.com.kernel2.httpAPI.HttpAPIResult;

/**
 * 期望接口返回的信息满足某些条件
 */
public interface Expecter{
	
	boolean match(HttpAPIResult result);
}