package jun.learn.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class CharsetUtil {
	
	@SuppressWarnings("resource")
	public static void exec(String filePath, String origin, String target) throws Exception {
		ByteBuffer buff = ByteBuffer.allocate(1024);
		// 固定编码读入
		FileChannel channel = new FileInputStream(filePath).getChannel();
		String text = read(buff, channel, origin);
		// 转码写入
		channel = new FileOutputStream(filePath).getChannel();
		write(buff, text.getBytes(target), channel);
		// 关闭资源
		channel.close();
	}
	
	private static String read(ByteBuffer buff, FileChannel channel, String charset) throws IOException {
		String result = "";
		int i = 0;
		while (i != -1) {
			i = channel.read(buff);
			buff.flip();
			byte[] dst = new byte[buff.remaining()];
			buff.get(dst);
			result += new String(dst, charset);
			buff.clear();
		}
		return result;
	}
	
	private static void write(ByteBuffer buff, byte[] box, FileChannel channel) throws IOException {
		int capacity = buff.capacity();
		int i = 0;
		while (i < box.length) {
			buff.clear();
			buff.put(box, i, box.length - i > capacity ? capacity : box.length - i);
			buff.flip();
			channel.write(buff);
			i += 1024;
		}
	}
	
	public static void change(String targetFile) throws Exception {
		File file = new File(targetFile);
		if (file.isFile()) {
			exec(targetFile, "gbk", "utf-8");
			return;
		} else {
			for (String file_t : file.list()) {
				change(targetFile + "\\" + file_t);
			}
		}
	}
	
	public static void main(String[] args) throws Exception {
		// CharsetUtil.exec("C:\\Users\\Administrator\\Desktop\\Magic.java", "gbk", "utf-8");
		// File file = new File("C:\\Users\\Administrator\\Desktop\\hello");
		// System.out.println(file.renameTo(new File("C:\\Users\\Administrator\\Desktop\\hello\\world")));
		change("C:\\Users\\Administrator\\Desktop\\com");
	}
}
