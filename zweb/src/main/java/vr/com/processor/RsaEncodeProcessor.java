package vr.com.processor;

import vr.com.util.VR_RSACoder;

public class RsaEncodeProcessor implements ValueProcessor{
	@Override
	public String process(String value) {
		String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ9mIWe63OiIDRh9invj8PL+xQoKIcn3zlu08GCqBzCwPxcMzSfDgzPrW22VCax30sqZnycMMWXesCZLHMW7gJYyVjfGo5/dsE0rKFJ7lExJfUNOBC7fkAzoz07qpkgDHHSKc11edT7cxCh/UrEEzjeQ4/6enixYzDoWfA8upL1QIDAQAB";
		return VR_RSACoder.encrypt(value, key);
	}

	@Override
	public String getName() {
		return "rsa_vrshow";
	}
}
