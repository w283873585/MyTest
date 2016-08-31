package vr.com.data;

import com.alibaba.fastjson.JSONObject;

public interface DataProvider {
	
	public String get(String key);
	
	public JSONObject getJson(String key);
	
	public <T> T getBean(String key);
	
	
	/**
	 * 	接口
	 * 	{
	 * 		url: "",
	 * 		desc: "",
	 * 		params: [{
	 *			key: "",
	 *			desc: ""
	 *			constraint: ""
	 * 		}]
	 * 	}
	 * 	
	 * 	测试用例
	 * 	{
	 * 		url: "",
	 * 		params: [],
	 * 		result: ""
	 * 	}
	 */
}
