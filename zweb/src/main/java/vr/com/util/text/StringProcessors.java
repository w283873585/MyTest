package vr.com.util.text;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import vr.com.util.text.encrypt.VR_DesUtils;
import vr.com.util.text.encrypt.VR_MD5Util;
import vr.com.util.text.encrypt.VR_RSACoder;

public enum StringProcessors {
	/** vrshow RSA **/
	vrshow_rsa_encrypt {
		@Override
		public String process(String str) {
			return VR_RSACoder.encrypt(str, vrshow_rsa_key);
		}
	},
	vrshow_rsa_decrypt {
		@Override
		public String process(String str) {
			return VR_RSACoder.decrypt(str, vrshow_rsa_key);
		}
	},
	
	/** vrdev RSA **/
	vrdev_rsa_encrypt {
		@Override
		public String process(String str) {
			return VR_RSACoder.encrypt(str, vrdev_rsa_key);
		}
	},
	vrdev_rsa_decrypt {
		@Override
		public String process(String str) {
			return VR_RSACoder.decrypt(str, vrdev_rsa_key);
		}
	},
	
	/** des **/
	des_encrypt {
		@Override
		public String process(String str) {
			try {
				return VR_DesUtils.encrypt(str);
			} catch (Exception e) {
				return null;
			}
		}
	},
	des_decrypt {
		@Override
		public String process(String str) {
			try {
				return VR_DesUtils.decrypt(str);
			} catch (Exception e) {
				return null;
			}
		}
	},
	
	/** urlEncode **/
	urlEncode_encrypt {
		@Override
		public String process(String str) {
			try {
				return URLEncoder.encode(str, charset);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	},
	urlEncode_decrypt {
		@Override
		public String process(String str) {
			try {
				return URLDecoder.decode(str, charset);
			} catch (UnsupportedEncodingException e) {
				return null;
			}
		}
	},
	
	md5 {
		@Override
		public String process(String str) {
			return VR_MD5Util.MD5(str);
		}
	};
	
	private static final String charset = "utf-8";
	private static final String vrshow_rsa_key = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDQ9mIWe63OiIDRh9invj8PL+xQoKIcn3zlu08GCqBzCwPxcMzSfDgzPrW22VCax30sqZnycMMWXesCZLHMW7gJYyVjfGo5/dsE0rKFJ7lExJfUNOBC7fkAzoz07qpkgDHHSKc11edT7cxCh/UrEEzjeQ4/6enixYzDoWfA8upL1QIDAQAB";
	private static final String vrdev_rsa_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0Wud7Sue3Fz8kvdNv4PSTMqpizXffNXgjxxEcIx9+Iw0l0CNnz/xzor+xp86QoY5Szb/0hlBC6t+Rg2BiQ4AxgSo2O/tjmF3km4aabdBnAi/D7DwnMJUhEf1JATVOM2oy631bSxSlwVzfq+9fXDHEEUYoyda3/y+1A7C0RA7iN49eFk5n/wiGZEaUrEbgo7Rk2Hv2pN42h5ZfQIFscqqt58Ql/79v7adKi6rMJX9AsrQaATUf502WOnTwucLc/QuK3uX30ywjygUCl9SGDhmSMvVmuGYuLkAq1FceI0QvvU+p72/fJ2dRvjXNgJG5lrJ2wvi+nKdTArieUkIf60UCwIDAQAB";
	
	public abstract String process(String str);
	
	public static void main(String[] args) {
		System.out.println(vrdev_rsa_encrypt.process("-1"));
	}
}
