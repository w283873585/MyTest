package vr.com.util.rsa;

import vr.com.util.text.encrypt.VR_RSACoder;

public class RSA_vrshow {
	private static String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ9mIWe63OiIDRh9invj8PL+xQoKIcn3zlu08GCqBzCwPxcMzSfDgzPrW22VCax30sqZnycMMWXesCZLHMW7gJYyVjfGo5/dsE0rKFJ7lExJfUNOBC7fkAzoz07qpkgDHHSKc11edT7cxCh/UrEEzjeQ4/6enixYzDoWfA8upL1QIDAQAB";
	public static String encrypt(String value) {
		return VR_RSACoder.encrypt(value, key);
	}
	
	public static String decrypt(String value) {
		return VR_RSACoder.decrypt(value, key);
	}
}
