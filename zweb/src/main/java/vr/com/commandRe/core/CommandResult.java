package vr.com.commandRe.core;

public interface CommandResult {
	
	String getResult();
	
	boolean isSuccess();
	
	CommandResult setRollbackCommand(String command);
}
