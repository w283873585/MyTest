package work;

import java.io.InputStream;
import java.net.URL;
import java.security.Key;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * DES加密/解密工具类
 * 
 * @author 易茂剑
 * 
 */
public class VR_DesUtils {

	// 算法名称
	private static final String KEY_ALGORITHM = "DES";

	// 算法名称/加密模式/填充方式
	// DES共有四种工作模式-->>ECB：电子密码本模式、CBC：加密分组链接模式、CFB：加密反馈模式、OFB：输出反馈模式
	private static final String CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	/**
	 * 
	 * 生成密钥key对象
	 * 
	 * @param KeyStr
	 *            密钥字符串
	 * @return 密钥对象
	 */
	private static SecretKey keyGenerator(String keyStr) throws Exception {
		byte input[] = HexString2Bytes(keyStr);
		DESKeySpec desKey = new DESKeySpec(input);
		// 创建一个密匙工厂，然后用它把DESKeySpec转换成
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		SecretKey securekey = keyFactory.generateSecret(desKey);
		return securekey;
	}

	private static int parse(char c) {
		if (c >= 'a')
			return (c - 'a' + 10) & 0x0f;
		if (c >= 'A')
			return (c - 'A' + 10) & 0x0f;
		return (c - '0') & 0x0f;
	}

	// 从十六进制字符串到字节数组转换
	private static byte[] HexString2Bytes(String hexstr) {
		byte[] b = new byte[hexstr.length() / 2];
		int j = 0;
		for (int i = 0; i < b.length; i++) {
			char c0 = hexstr.charAt(j++);
			char c1 = hexstr.charAt(j++);
			b[i] = (byte) ((parse(c0) << 4) | parse(c1));
		}
		return b;
	}

	/**
	 * 加密数据
	 * 
	 * @param data
	 *            待加密数据
	 * @return 加密后的数据
	 */
	public static String encrypt(String data) throws Exception {
		Key deskey = keyGenerator("020100300D06092A864886F70D01010105000430819F300D06092A864886F70D010101050003818D00308189028");
		// 实例化Cipher对象，它用于完成实际的加密操作
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		SecureRandom random = new SecureRandom();
		// 初始化Cipher对象，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, deskey, random);
		byte[] results = cipher.doFinal(data.getBytes());
		// 执行加密操作。加密后的结果通常都会用Base64编码进行传输
		return Base64.encodeBase64String(results);
	}

	/**
	 * 解密数据
	 * 
	 * @param data
	 *            待解密数据
	 * @return 解密后的数据
	 */
	public static String decrypt(String data) throws Exception {
		Key deskey = keyGenerator("020100300D06092A864886F70D01010105000430819F300D06092A864886F70D010101050003818D00308189028");
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM);
		// 初始化Cipher对象，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, deskey);
		// 执行解密操作
		return new String(cipher.doFinal(Base64.decodeBase64(data)));
	}

	/**
	 * 测试
	 */
	public static void main(String[] args) throws Exception {
		String source = "13537806207";
		System.out.println("原文: " + source);
		String encryptData = encrypt(source);
		System.out.println("加密后: " + encryptData);
		String decryptData = decrypt(encryptData);
		System.out.println("解密后: " + decryptData);
		
		URL url = new URL("http", "www.baidu.com", 80, "");
		System.out.println(url.openConnection());
		InputStream a = url.openStream();
		int i = 0;
		byte[] b = new byte[1024];
		while (i != -1) {
			i = a.read(b);
			System.out.println(new String(b));
		}
		a.close();
	}

}
