package learn.atest;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class Test {
	public static void main(String[] args) throws IOException {
		File file = new File("C:\\Users\\Administrator\\Desktop\\tian.txt");
		FileChannel channel =  new FileInputStream(file).getChannel();
		ByteBuffer buff = ByteBuffer.allocate(1024);
		int i = 0;
		while (i != -1) {
			i = channel.read(buff);
			output(buff);
		}
		channel.close();
	}
	
	private static void output(ByteBuffer buff) throws UnsupportedEncodingException {
		buff.flip();
		byte[] arr = buff.array();
		System.out.println(new String(arr, "utf8"));
		/*
		while (buff.hasRemaining()) {
			System.out.print(buff.get());
		}
		*/
		buff.clear();
	}
}
