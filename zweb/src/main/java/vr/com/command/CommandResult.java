package vr.com.command;


public interface CommandResult {
	
	String getResult();
	
	boolean isSuccess();
	
	void setRollbackCommand(String command);
	
	void setOriginCommand(String command);
}
