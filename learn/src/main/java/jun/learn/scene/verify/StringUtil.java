package jun.learn.scene.verify;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.Random;
import java.util.UUID;

public class StringUtil {
	private static char ch[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1' };// 最后又重复两个0和1，因为需要凑足数组长度为64

	private static Random random = new Random();

	/**
	 * 生成指定长度的随机字符串
	 */
	protected static synchronized String createRandomString(int length) {
		if (length > 0) {
			int index = 0;
			char[] temp = new char[length];
			int num = random.nextInt();
			for (int i = 0; i < length % 5; i++) {
				temp[index++] = ch[num & 63];// 取后面六位，记得对应的二进制是以补码形式存在的。
				num >>= 6;// 63的二进制为:111111
				// 为什么要右移6位？因为数组里面一共有64个有效字符。为什么要除5取余？因为一个int型要用4个字节表示，也就是32位。
			}
			for (int i = 0; i < length / 5; i++) {
				num = random.nextInt();
				for (int j = 0; j < 5; j++) {
					temp[index++] = ch[num & 63];
					num >>= 6;
				}
			}
			return new String(temp, 0, length);
		} else if (length == 0) {
			return "";
		} else {
			throw new IllegalArgumentException();
		}
	}

	/**
	 * 生成一个32位MD5的随机串
	 */
	public static String createMd5Str() {
		StringBuffer sb = new StringBuffer("");
		sb.append(System.currentTimeMillis());
		sb.append(UUID.randomUUID());
		sb.append(createRandomString(32));
		return getMd5String(sb.toString());
	}

	/**
	 * MD5加密
	 */
	public static String getMd5String(String plainText) {
		String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(plainText.getBytes());
			byte b[] = md.digest();
			int i;
			StringBuffer buf = new StringBuffer("");
			for (int offset = 0; offset < b.length; offset++) {
				i = b[offset];
				if (i < 0)
					i += 256;
				if (i < 16)
					buf.append("0");
				buf.append(Integer.toHexString(i));
			}
			result = buf.toString();

		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 格式化字符串
	 */
	public static String formatNumberStr(Integer num) {
		return new DecimalFormat("0000").format(num);
	}

	/**
	 * 生成指定长度的随机数
	 */
	public static String genRandomNum(int len) {
		final int maxNum = 36;
		int i; // 生成的随机数
		int count = 0; // 生成的密码的长度
		char[] str = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
		StringBuffer pwd = new StringBuffer("");
		Random r = new Random();
		while (count < len) {
			// 生成随机数，取绝对值，防止生成负数，
			i = Math.abs(r.nextInt(maxNum)); // 生成的数最大为36-1
			if (i >= 0 && i < str.length) {
				pwd.append(str[i]);
				count++;
			}
		}
		return pwd.toString();
	}
	/**
	 * 
	 */
	public static boolean isEmpty(String val) {
		return val == null || "".equals(val);
	}
	
	public static boolean isNotEmpty(String val) {
		return val != null && !"".equals(val);
	}
	
	public static String toUpperCaseFirst(String val) {
		return val.substring(0, 1).toUpperCase() + val.substring(1);
	}
}
