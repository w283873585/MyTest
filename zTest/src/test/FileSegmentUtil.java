package test;

import java.nio.ByteBuffer;

import test.FileUtil.Consumer;


public class FileSegmentUtil {
	
	public static String read(String path) {
		final StringBuilder result = new StringBuilder();
		FileUtil.read(path, new Consumer() {
			public void consume(ByteBuffer buff) {
				result.append(new String(buff.array()));
			}
		});
		return result.toString();
	}
}