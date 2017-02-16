package vr.com.kernel2.chain;

import com.alibaba.fastjson.JSONObject;

/**
 * 期望接口返回的信息满足某些条件
 */
public interface Expecter{
	
	boolean match(JSONObject result);
}