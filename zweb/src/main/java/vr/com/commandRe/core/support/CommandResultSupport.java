package vr.com.commandRe.core.support;

import org.springframework.data.mongodb.core.mapping.Document;

import vr.com.commandRe.core.CommandResult;
import vr.com.util.JsonUtil;

@Document(collection="CommandResult")
public class CommandResultSupport implements CommandResult {
	
	private String result;
	private boolean success;
	private String rollbackCommand;
	private String originCommand;
	
	public CommandResultSupport() {}
	
	public CommandResultSupport(boolean isSuccess, String data) {
		this.result = data;
		this.success = isSuccess;
	}
	
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getRollbackCommand() {
		return this.rollbackCommand;
	}
	
	public void setRollbackCommand(String command) {
		this.rollbackCommand = command;
	}
	
	public void setOriginCommand(String command) {
		this.originCommand = command;
	}

	public String getOriginCommand() {
		return originCommand;
	}
	
	public String toString() {
		return JsonUtil
				.putA("state", success ? "执行成功" : "执行失败")
				.putA("data", result)
				.putA("rollbackCommand", rollbackCommand)
				.toJSONString();
	}
	
	public static CommandResultSupport error(String data) {
		return new CommandResultSupport(false, data);
	}
	
	public static CommandResultSupport success(String data) {
		return new CommandResultSupport(true, data);
	}
}
