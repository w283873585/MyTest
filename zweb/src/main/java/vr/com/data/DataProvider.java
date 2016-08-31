package vr.com.data;

public interface DataProvider {
	
	DataProvider addCondition(Condition c);
	
	DataProvider addFilter(Filter f);
	
	DataProvider addEntity(String entity);
	
	String invoke(Command command);
	
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
