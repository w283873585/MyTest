package jun.learn.foundation.base;

import java.io.UnsupportedEncodingException;

public class StringEncode {
	public static void main(String[] args) throws UnsupportedEncodingException {
		char c = '中';
		System.out.println((int) c);
		byte a = (byte) c;
		System.out.println(a);		  // 45
		System.out.println((char) a); // - 
		/**
		 * 数值被破坏
		 * 因为char是16位的  即 111111111111111
		 * 而byte是8位的, 肯定是还原不了的
		 * byte很重要，java中数据单位最小就是byte，数据传输最小单位也是byte
		 */
		// 编码格式：
		// iso8859-1 latin1  单字节格式， 向下兼容ASCII
		// utf8 		 	多字节格式， 基于unicode，但为了省空间用一定的格式存储
		// gbk gb2312 	 	多字节格式， 基于unicode，但为了省空间用一定的格式存储
		// unicode 			多字节格式，占用空间大
		char d = '中';			
		String str = "中国";		
		/** 
		  	char类型是以unicode码存储的。
			string是一个char数组，本质也是无编码格式的。
			所谓的编码格式是基于byte数组的，
			new String(byteArr, "utf-8"); 是将byteArr以utf-8格式去解码
			getBytes("utf-8") 以utf-8格式将string编码为byte数组
		*/
		// 正确编码，正确解码
		byte[] barr = str.getBytes("utf-8");	
		System.out.println("正确解码->" + new String(barr, "utf-8"));
		/**
			为什么以byte数组的格式传输数据，
		 	因为byte数组可以用一定编码格式压缩，并且作为最小单位的容器，很方便
		*/
		// 用gbk格式编码，然后用iso8859-1去解码，
		String str1 = new String(str.getBytes("gbk"), "iso8859-1");
		// 用相反的方式还原
		System.out.println("-->" + new String(str1.getBytes("iso8859-1"), "gbk"));
		
		// 同理
		String str2 = new String(str.getBytes("utf-8"), "iso8859-1");
		System.out.println(new String(str2.getBytes("iso8859-1"), "utf-8"));
		
		// 同理
		String str3 = new String(str.getBytes("utf-8"), "gbk");
		System.out.println(new String(str3.getBytes("gbk"), "utf-8"));
	
		// 错误的方式编码（因为iso8859-1不支持中文）
		// 用iso8859-1编码中文，则还原不了，因为解码不出正确的unicode码了
		String str4 = new String(str.getBytes("iso8859-1"), "iso8859-1");
		System.out.println(str4);
	}
}
