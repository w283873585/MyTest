package test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

import test.FileUtil.Consumer;

public class Kernel2 {
	private static final String CHARSET = "GBK";
	
	public static List<String> getResources(String path) {
		final List<String> result = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder();
		FileUtil.read(path, new Consumer() {
			public void consume(ByteBuffer buff) {
				CharBuffer cBuff = Charset.forName(CHARSET).decode(buff);
				while (cBuff.hasRemaining()) {
					char cur = cBuff.get();
					if (cur != '\r') {
						sb.append(cur);
					} else {
						result.add(sb.toString());
						sb.delete(0, sb.length());
					}
				}
			}
		});
		return new ArrayList<String>(result);
	}
	public static void main(String[] args) {
		List<String> list = Kernel2.getResources("C:/Users/Administrator/Desktop/b.txt");
		 System.out.println(list.size());
		 System.out.println(list.get(122));
		 System.out.println(list.get(100));
	}
}
