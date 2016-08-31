package vr.com.kernel.processor;

import vr.com.util.VR_RSACoder;

public class DevRsaEncodeProcessor implements ValueProcessor{
	
	@Override
	public String process(String value) {
		String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Wud7Sue3Fz8kvdNv4PSTMqpizXffNXgjxxEcIx9+Iw0l0CNnz/xzor+xp86QoY5Szb/0hlBC6t+Rg2BiQ4AxgSo2O/tjmF3km4aabdBnAi/D7DwnMJUhEf1JATVOM2oy631bSxSlwVzfq+9fXDHEEUYoyda3/y+1A7C0RA7iN49eFk5n/wiGZEaUrEbgo7Rk2Hv2pN42h5ZfQIFscqqt58Ql/79v7adKi6rMJX9AsrQaATUf502WOnTwucLc/QuK3uX30ywjygUCl9SGDhmSMvVmuGYuLkAq1FceI0QvvU+p72/fJ2dRvjXNgJG5lrJ2wvi+nKdTArieUkIf60UCwIDAQAB";
		return VR_RSACoder.encrypt(value, key);
	}

	@Override
	public String getName() {
		return "rsa_vrdev";
	}
}
