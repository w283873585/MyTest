package vr.com.command.support;

import com.alibaba.fastjson.JSONObject;

import vr.com.command.CommandResult;
import vr.com.command.impl.BaseDbCommand.QueryType;

public class DbCommandVO {
	
	private final String name;
	private final QueryType type;
	
	private DbCommandVO(String name, QueryType type) {
		this.name = name;
		this.type = type;
	}
	
	public ExecutableDbCommand addParam(String... keyOrValue) {
		if (keyOrValue.length % 2 != 0)
			throw new IllegalArgumentException("参数错误");
		
		ExecutableDbCommand command = new ExecutableDbCommand(name, type.toString());
		int i = 0;
		while (i < keyOrValue.length) {
			command.addParam(keyOrValue[i], keyOrValue[i + 1]);
			i += 2;
		}
		return command;
	}
	
	public static DbCommandVO build(String name, QueryType type) {
		return new DbCommandVO(name, type);
	}
	
	public static class ExecutableDbCommand {
		private String name;
		private String type;
		private JSONObject param = new JSONObject();
		
		private ExecutableDbCommand(String name, String type) {
			this.name = name;
			this.type = type;
		}
		
		public void addParam(String key, String value) {
			param.put(key, value);
		}
		
		public CommandResult execute(CommandManager manager) {
			return manager.exec(this.toString(), true);
		}
		
		public String toString() {
			return "CommonDb " 
					+ "name=" + name
					+ "&type=" + type
					+ "&param=" + param.toJSONString();
		}
	}
}
