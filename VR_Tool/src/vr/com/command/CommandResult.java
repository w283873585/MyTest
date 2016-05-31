package vr.com.command;

import vr.com.util.JsonUtil;

public class CommandResult {
	private final boolean isSuccess;
	private final String data;
	
	private CommandResult(boolean isSuccess, String data) {
		this.isSuccess = isSuccess;
		this.data = data;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public String toString() {
		return JsonUtil
				.putA("state", isSuccess ? "执行成功" : "执行失败")
				.putA("data", data).toJSONString();
	}
	
	public static CommandResult error(String data) {
		return new CommandResult(false, data);
	}
	
	public static CommandResult success(String data) {
		return new CommandResult(true, data);
	}
}
