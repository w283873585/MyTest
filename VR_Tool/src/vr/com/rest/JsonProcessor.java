package vr.com.rest;

import com.alibaba.fastjson.JSON;

public class JsonProcessor implements ValueProcessor{

	@Override
	public String getName() {
		return "json";
	}

	@Override
	public Object process(String value) {
		return JSON.parse(value);
	}
}
