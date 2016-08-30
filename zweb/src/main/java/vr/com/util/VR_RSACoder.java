package vr.com.util;

import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

/**
 * RSA安全编码组件
 * 
 * @author 易茂剑
 */
public abstract class VR_RSACoder extends VR_Coder {

	private static final String KEY_ALGORITHM = "RSA";

	/**
	 * 公钥解密
	 */
	protected static byte[] decryptByPublicKey(byte[] data, String key) throws Exception {
		// 对密钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据解密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.DECRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}

	/**
	 * 公钥加密
	 */
	protected static byte[] encryptByPublicKey(byte[] data, String key) throws Exception {
		// 对公钥解密
		byte[] keyBytes = decryptBASE64(key);

		// 取得公钥
		X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
		Key publicKey = keyFactory.generatePublic(x509KeySpec);

		// 对数据加密
		Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);

		return cipher.doFinal(data);
	}
	
	public static String encrypt(String value, String key) {
		String val = null;
		try {
			val = VR_Base64.encode(encryptByPublicKey(value.getBytes(), key));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
	
	public static String decrypt(String value, String key) {
		String val = null;
		try {
			byte[] data = VR_Base64.decode(value);
			// 参数字符串转成byte数组并进行解密
			byte[] decodedData = decryptByPublicKey(data, key);
			val = new String(decodedData);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return val;
	}
}
