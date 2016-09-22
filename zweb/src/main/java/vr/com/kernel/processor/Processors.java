package vr.com.kernel.processor;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import com.alibaba.fastjson.JSON;

import vr.com.util.VR_DesUtils;
import vr.com.util.VR_MD5Util;
import vr.com.util.VR_RSACoder;

public enum Processors implements ValueProcessor{
	json {
		public Object process(String value) {
			return JSON.parse(value);
		}
	},
	
	desencode {
		public Object process(String value) {
			try {
				return VR_DesUtils.encrypt(value);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}
		}
	},
	
	urlencode {
		public Object process(String value) {
			try {
				return URLEncoder.encode(value, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
				return null;
			}
		}
	},
	
	rsa_vrdev {
		public Object process(String value) {
			String key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Wud7Sue3Fz8kvdNv4PSTMqpizXffNXgjxxEcIx9+Iw0l0CNnz/xzor+xp86QoY5Szb/0hlBC6t+Rg2BiQ4AxgSo2O/tjmF3km4aabdBnAi/D7DwnMJUhEf1JATVOM2oy631bSxSlwVzfq+9fXDHEEUYoyda3/y+1A7C0RA7iN49eFk5n/wiGZEaUrEbgo7Rk2Hv2pN42h5ZfQIFscqqt58Ql/79v7adKi6rMJX9AsrQaATUf502WOnTwucLc/QuK3uX30ywjygUCl9SGDhmSMvVmuGYuLkAq1FceI0QvvU+p72/fJ2dRvjXNgJG5lrJ2wvi+nKdTArieUkIf60UCwIDAQAB";
			return VR_RSACoder.encrypt(value, key);
		}
	},
	
	rsa_vrshow {
		public Object process(String value) {
			String key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ9mIWe63OiIDRh9invj8PL+xQoKIcn3zlu08GCqBzCwPxcMzSfDgzPrW22VCax30sqZnycMMWXesCZLHMW7gJYyVjfGo5/dsE0rKFJ7lExJfUNOBC7fkAzoz07qpkgDHHSKc11edT7cxCh/UrEEzjeQ4/6enixYzDoWfA8upL1QIDAQAB";
			return VR_RSACoder.encrypt(value, key);
		}
	},
	
	md5 {
		public Object process(String value) {
			return VR_MD5Util.MD5(value);
		}
	};

	@Override
	public String getName() {
		return this.name();
	}

	public abstract Object process(String value);
}
