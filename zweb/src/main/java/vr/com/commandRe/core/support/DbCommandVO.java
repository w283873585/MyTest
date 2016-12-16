package vr.com.commandRe.core.support;

import com.alibaba.fastjson.JSONObject;

import vr.com.commandRe.core.impl.BaseDbCommand.QueryType;

public class DbCommandVO {
	
	private String name;
	private QueryType type;
	private JSONObject param = new JSONObject();
	
	private DbCommandVO(String name, QueryType type) {
		this.name = name;
		this.type = type;
	}
	
	public DbCommandVO addParam(String key, String value) {
		param.put(key, value);
		return this;
	}
	
	public String toString() {
		return "CommonDb " 
				+ "name=" + name
				+ "&type=" + type
				+ "&param=" + param.toJSONString();
	}
	
	public static DbCommandVO build(String name, QueryType type) {
		return new DbCommandVO(name, type);
	}
}
