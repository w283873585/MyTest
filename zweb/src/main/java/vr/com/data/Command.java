package vr.com.data;


public interface Command {
	
	public Result exec(DataProvider provider);
}
