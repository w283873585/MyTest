package vr.com.rest;

import vr.com.util.VR_MD5Util;

public class MD5Processor implements ValueProcessor{

	@Override
	public String getName() {
		return "md5";
	}

	@Override
	public String process(String value) {
		return VR_MD5Util.MD5(value);
	}
	
}
