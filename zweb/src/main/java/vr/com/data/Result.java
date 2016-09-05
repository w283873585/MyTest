package vr.com.data;

import java.util.List;

import com.alibaba.fastjson.JSONObject;

public interface Result {
	public <T> T getBean(Class<T> c);
	
	public <T> List<T> getList(Class<T> c);
	
	public String getString();
	
	public JSONObject getJSON();
}
