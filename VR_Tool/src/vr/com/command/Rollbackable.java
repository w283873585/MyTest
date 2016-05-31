package vr.com.command;

public interface Rollbackable {
	public CommandResult rollback(String paramsInfo);
}
