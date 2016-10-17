package vr.com.util.security;



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
		
		byte[] result = rsa.encrypt(Base16.decode(msg));
		
		return Base16.encode(result);
	}
	
	public static String rsaEncode1(String msg) throws Exception {
		
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
	
	public static void main(String[] args) throws Exception {
		System.out.println(rsaEncode1("fedcba9876543210"));
		System.out.println(rsaDecode(rsaEncode1("fedcba9876543210")));
		System.out.println(rsaDecode("425B800E87F5199E30F184F3C81E96A2BE98958BC22C69BA2F254541AF517E12CC99DFEDFB56301CA1C82F4E527BABA4CF22C8BF25AEEC03F1C8BF431FDDB5800954F14A27D29E71EC1D6DF7205D29CA17FC21CD269EB0C96F29BE3D2F916D80BE8FAFD1F267D9AB481BD5542934CBF29F90AFDE76EB039430B767A26C24DEC1"));
	}
}
