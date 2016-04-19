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

public class FileUtil1 {
	private FileChannel channel;
	private ByteBuffer buff;
	
	public List<String> read(String path, Consumer consumer) {
		List<String> result = new ArrayList<String>();
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			buff = ByteBuffer.allocate(1024);
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
	}
	
	public static interface Consumer{
		public void consume(Carrier carrier);
	}
	
	
	public static void main(String[] args) {
		final List<String> result = new ArrayList<String>();
		final StringBuilder sb = new StringBuilder();
		FileUtil1 util = new FileUtil1();
		util.read("C:/Users/Administrator/Desktop/b.txt", new Consumer() {
			public void consume(Carrier carrier) {
				ByteBuffer buff = carrier.get();
				while (buff.hasRemaining()) {
					CharBuffer cBuff = Charset.forName("GBK").decode(buff);
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
					buff = carrier.get();
				}
			}
		});
		System.out.println(result);
	}
}
