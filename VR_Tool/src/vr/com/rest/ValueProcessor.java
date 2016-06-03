package vr.com.rest;

import vr.com.kernel.Denominative;

public interface ValueProcessor extends Denominative{
	
	/**
	 * 加工字符
	 * @param value
	 * @return
	 */
	public Object process(String value);
}
