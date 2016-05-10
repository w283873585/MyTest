package learn.file.util;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.Map;

public class FileUtil {
	public static void read(String path, Consumer consumer) {
		FileChannel channel = null;
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(path);
			channel = fis.getChannel();
			final ByteBuffer buff = ByteBuffer.allocate(1024);
			int i = 0;
			while (i != -1) {
				i = channel.read(buff);
				buff.flip();
				consumer.consume(buff);
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
	
	public static interface Consumer{
		public void consume(ByteBuffer buff);
	}
	
	
	public static void main(String[] args) {
		final Map<String, String> map = new HashMap<String, String>();
		FileUtil.read("", new Consumer() {
			public void consume(ByteBuffer buff) {
				map.put("1", new String(buff.array()));
			}
		});
	}
}
