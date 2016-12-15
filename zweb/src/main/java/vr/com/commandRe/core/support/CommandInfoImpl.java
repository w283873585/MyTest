package vr.com.commandRe.core.support;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vr.com.commandRe.core.CommandInfo;

public class CommandInfoImpl implements CommandInfo{

	private String command;
	
	private Map<String, String> param = new HashMap<String, String>();
	
	public CommandInfoImpl(String commnadName) {
		this.command = commnadName;
	}
	
	public void put(String key, String value) {
		this.param.put(key, value);
	}
	
	public String getCommandName() {
		return this.command;
	}
	
	public Map<String, String> getParam() {
		return this.param;
	}

	public String get(String key) {
		return param.get(key);
	}

	public Iterator<String> iterator() {
		return param.keySet().iterator();
	}

	public boolean contains(String key) {
		return param.containsKey(key);
	}
}
