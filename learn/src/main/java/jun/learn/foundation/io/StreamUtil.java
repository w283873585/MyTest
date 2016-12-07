package jun.learn.foundation.io;

import java.io.InputStream;
import java.net.URL;

public class StreamUtil {
	public static interface Task{
		public void invoke(byte[] b);
	}
	public static void out(InputStream input, int capacity, Task task) {
		byte[] buff = new byte[capacity];
		int i = 0;
		try {
			while (i != -1) {
				i = input.read(buff);
				task.invoke(buff);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void out(InputStream input, Task task) {
		out(input, 1024, task);
	}
	public static void main(String[] args) throws Exception {
		URL url = new URL("http", "www.vrshow.com", 80, "");
		InputStream input = url.openStream();
		StreamUtil.out(input, new Task() {
			public void invoke(byte[] b) {
				System.out.println(new String(b));
			}
		});
		input.close();
	}
}
