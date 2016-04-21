package test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class FileUtil_SelfDriving {
	private FileChannel channel;
	private ByteBuffer buff;
	private int capacity = 1024;
	private String charset = "GBK";
	
	public FileUtil_SelfDriving() {}
	public FileUtil_SelfDriving(int capacity) { this.capacity = capacity; }
	public FileUtil_SelfDriving(String charset) { this.charset = charset; }
	public FileUtil_SelfDriving(int capacity, String charset) {
		this.capacity = capacity; 
		this.charset = charset;
	}
	
	public List<String> read(String path, Consumer consumer) {
		List<String> result = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			buff = ByteBuffer.allocate(capacity);
			channel = fis.getChannel();
			consumer.consume(new Carrier());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				if (channel != null) channel.close();
				if (fis != null) fis.close();
			} catch (IOException e) {}
		}
		return result;
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
			return Charset.forName(charset).decode(get());
		}
	}
	
	public static interface Consumer{
		public void consume(Carrier carrier);
	}
	
	
	public static void main(String[] args) {
		final List<String> result = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder();
		FileUtil_SelfDriving util = new FileUtil_SelfDriving();
		util.read("C:/Users/Administrator/Desktop/b.txt", new Consumer() {
			public void consume(Carrier carrier) {
				CharBuffer buff = carrier.getChar();
				while (buff.hasRemaining()) {
					char cur = buff.get();
					if (cur != '\r') {
						sb.append(cur);
					} else {
						result.add(sb.toString());
						sb.delete(0, sb.length());
					}
					buff = carrier.getChar();
				}
			}
		});
		System.out.println(result);
	}
}
