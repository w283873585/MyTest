package learn.file.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class Kernel {
	private static final int CAPACITY = 1024;
	private static final String CHARSET = "GBK";
	private String filePath = null;
	private List<String> result = new ArrayList<String>();
	private StringBuilder sb = new StringBuilder();
	
	public Kernel(String filePath) {
		this.filePath = filePath;
	}
	
	public List<String> getResources() {
		FileChannel channel = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(filePath);
			channel = fis.getChannel();
			ByteBuffer buff = ByteBuffer.allocate(CAPACITY);
			int i = 0;
			while (i != -1) {
				i = channel.read(buff);
				buff.flip();
				out(buff);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null) channel.close();
				if (fis != null) fis.close();
			} catch (IOException e) {}
		}
		return result;
	}
	
	private void out(ByteBuffer buff) throws UnsupportedEncodingException {
		CharBuffer cBuff = Charset.forName(CHARSET).decode(buff);
		buff.clear();
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
	public static void main(String[] args) {
		 Kernel k = new Kernel("C:/Users/Administrator/Desktop/b.txt");
		 List<String> list = k.getResources();
		 System.out.println(list.size());
		 System.out.println(list.get(122));
		 System.out.println(list.get(100));
	}
}
