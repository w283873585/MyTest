package vr.com.data;

import java.io.Closeable;

public interface DataResource extends Closeable{
	
	void initialize();
	
	DataProvider getProvider(String name);
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
