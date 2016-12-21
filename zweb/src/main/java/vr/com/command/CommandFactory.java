package vr.com.command;

public interface CommandFactory {
	
	Command create(String commandName);
}
