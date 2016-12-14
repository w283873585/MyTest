package vr.com.commandRe.core.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import vr.com.commandRe.core.CommandInfo;

public class CommandInfoImpl implements CommandInfo{

	private String command;
	
	private Map<String, Object> param = new HashMap<String, Object>();
	
	public CommandInfoImpl(String commnadName) {
		this.command = commnadName;
	}
	
	public void put(String key, Object value) {
		this.param.put(key, value);
	}
	
	public String getCommandName() {
		return this.command;
	}
	
	public Map<String, Object> getParam() {
		return this.param;
	}

	public Object get(String key) {
		return param.get(key);
	}

	public Iterator<String> iterator() {
		return param.keySet().iterator();
	}
}
