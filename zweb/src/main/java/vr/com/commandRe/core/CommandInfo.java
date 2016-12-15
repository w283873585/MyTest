package vr.com.commandRe.core;

import java.util.Iterator;
import java.util.Map;

public interface CommandInfo extends Iterable<String>{
	
	public String getCommandName();
	
	public String get(String key);
	
	public boolean contains(String key);
	
	public Iterator<String> iterator();
	
	public Map<String, String> getParam();
}
