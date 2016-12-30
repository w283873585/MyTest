package jun.learn.scene.softChain.security;



public class VR_Encrypt_Util {
	
	
	private static final String CHARSET = "utf-8";
	
	/**
	 * 解密流程
	 * 
	 * rsa解密aeskey, rsa解密aesIv
	 * 
	 * aes解密msg
	 */
	
	
	/**
	 * 加密流程
	 * 
	 * msg, aes加密
	 * 
	 * 输出
	 * {"msg":"47140BFCCEDBFE37B53E7225699D065C69FDE709ECC5082B4F7ABA8490B3404B","length":"32","packageId":"1271"}
	 * 
	 */
	
	/**
	 * rsa加密
	 */
	public static String rsaEncode(String msg) throws Exception {
		
		// RSA 解密
		RSA rsa = new RSA();
		
		rsa.initPublicKey(CommonConstant.RSA_N, CommonConstant.RSA_E);
		
		byte[] result = rsa.encrypt(msg.getBytes(CHARSET));
		
		return Base16.encode(result);
	}
	
	
	/**
	 * rsa解密
	 */
	public static String rsaDecode(String msg) throws Exception {
        // RSA 解密
        RSA dec = new RSA();
        
        dec.initPrivateKey(CommonConstant.RSA_N, CommonConstant.RSA_E, CommonConstant.RSA_D);
       
        byte[] result = dec.decrypt(Base16.decode(msg));

        return new String(result, CHARSET);
	}
	
	/**
	 * aes加密
	 */
	public static String aesEncode(String source, String aeskey, String aesIv) throws Exception {
		return new String(aesEncodeToByte(source, aeskey, aesIv), CHARSET);
	}
	
	
	/**
	 * aes加密 + Base16
	 */
	public static String aesAndBase16Encode(String source, String aeskey, String aesIv) throws Exception {
		return Base16.encode(aesEncodeToByte(source, aeskey, aesIv));
	}
	
	/**
	 * aes辅助方法 
	 */
	public static byte[] aesEncodeToByte(String source, String aeskey, String aesIv) throws Exception {
		AES aes = new AES();
		aes.init(aeskey.getBytes(CHARSET), aesIv.getBytes(CHARSET));
		return aes.encrypt(source.getBytes(CHARSET));
	}
	
	
	/**
	 * aes解密
	 */
	public static String aesDecode(String encrytMsg, String aeskey, String aesIv) throws Exception {
	
		byte[] msgs = Base16.decode(encrytMsg);
		
		AES aes = new AES();
		aes.init(aeskey.getBytes(CHARSET), aesIv.getBytes(CHARSET));
		
		return new String(aes.decrypt(msgs), CHARSET);
	}
}
