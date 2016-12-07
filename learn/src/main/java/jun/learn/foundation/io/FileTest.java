package jun.learn.foundation.io;

import java.io.File;
import java.io.IOException;

public class FileTest {
	public static void main(String[] args) {
		System.out.println(Thread.currentThread());
		System.out.println(FileTest.class.getResource("").getPath());
		File file = new File("C:\\Users\\lenovo\\Desktop\\test\\");
		System.out.println(file.separatorChar);
		System.out.println(file.listFiles());
		System.out.println(file);
		// file.li
	}
}
