package vr.com.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtil_Mixin {
	private FileChannel channel;
	private ByteBuffer buff;
	private int capacity = 1024;
	private String charset = "GBK";
	
	public FileUtil_Mixin() {}
	public FileUtil_Mixin(int capacity) { this.capacity = capacity; }
	public FileUtil_Mixin(String charset) { this.charset = charset; }
	public FileUtil_Mixin(int capacity, String charset) {
		this.capacity = capacity; 
		this.charset = charset;
	}
	
	public void read(String path, Consumer consumer) {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			buff = ByteBuffer.allocate(capacity);
			channel = fis.getChannel();
			int i = 0;
			while (i != -1) {
				i = channel.read(buff);
				buff.flip();
				consumer.consume(buff, new Carrier());
				buff.clear();
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
	}
	
	public class Carrier{
		private Carrier() {}
		public ByteBuffer get() {
			try {
				buff.clear();
				channel.read(buff);
				buff.flip();
			} catch (IOException e) {}
			return buff;
		}
		public CharBuffer getChar() {
			return getChar(get());
		}
		public CharBuffer getChar(ByteBuffer buff) {
			return Charset.forName(charset).decode(buff);
		}
	}
	
	public static interface Consumer{
		public void consume(ByteBuffer buff, Carrier carrier);
	}
	
	
	public static void main(String[] args) {
		final List<String> result = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder();
		FileUtil_Mixin util = new FileUtil_Mixin();
		util.read("C:/Users/xnxs/Desktop/selftest/b.txt", new Consumer() {
			public void consume(ByteBuffer buff, Carrier carrier) {
				CharBuffer cBuff = Charset.forName("GBK").decode(buff);
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
		System.out.println(result);
	}
}
