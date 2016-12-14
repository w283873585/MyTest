package vr.com.commandRe.core;

import java.util.Iterator;
import java.util.Map;

public interface CommandInfo extends Iterable<String>{
	
	public String getCommandName();
	
	public Object get(String key);
	
	public Iterator<String> iterator();
	
	public Map<String, Object> getParam();
}
