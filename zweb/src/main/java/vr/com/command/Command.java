package vr.com.command;

public interface Command {
	CommandResult exec(String commandInfo);
	
	String getName();
	
}
