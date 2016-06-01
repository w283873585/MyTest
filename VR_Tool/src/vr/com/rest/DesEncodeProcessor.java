package vr.com.rest;

import vr.com.util.VR_DesUtils;

public class DesEncodeProcessor implements ValueProcessor{

	@Override
	public String getName() {
		return "desencode";
	}

	@Override
	public String process(String value) {
		try {
			return VR_DesUtils.encrypt(value);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
