package vr.com.command;

public interface Command {
	CommandResult exec(String paramsInfo);
	
	String getName();
}
