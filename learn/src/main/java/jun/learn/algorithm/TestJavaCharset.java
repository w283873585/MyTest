package jun.learn.algorithm;

import java.io.UnsupportedEncodingException;

public class TestJavaCharset {
	
	
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "�л����񹲺͹�";
		String str01 = new String(str.getBytes("gbk"),"ISO8859-1");
		System.out.println(new String(str01.getBytes("ISO8859-1"),"gbk"));
	}
}
