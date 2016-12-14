package vr.com.commandRe.core.impl;

import vr.com.commandRe.core.CommandResult;
import vr.com.util.JsonUtil;

public class CommandResultImpl implements CommandResult {
	private final String data;
	private final boolean isSuccess;
	private String rollbackCommand;
	
	private CommandResultImpl(boolean isSuccess, String data) {
		this.data = data;
		this.isSuccess = isSuccess;
	}
	
	public boolean isSuccess() {
		return isSuccess;
	}
	
	public String getResult() {
		return data;
	}
	
	public CommandResult setRollbackCommand(String command) {
		this.rollbackCommand = command;
		return this;
	}
	
	public String toString() {
		return JsonUtil
				.putA("state", isSuccess ? "执行成功" : "执行失败")
				.putA("data", data)
				.putA("rollbackCommand", rollbackCommand)
				.toJSONString();
	}
	
	public static CommandResult error(String data) {
		return new CommandResultImpl(false, data);
	}
	
	public static CommandResult success(String data) {
		return new CommandResultImpl(true, data);
	}

}
