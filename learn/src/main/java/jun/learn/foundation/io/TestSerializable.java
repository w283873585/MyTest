package jun.learn.foundation.io;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class TestSerializable implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1957775530535472064L;
	
	public String name = "junq";
	
	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		try {
			TestSerializable test = new TestSerializable();
			test.name = "weijun";
			ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(new File("C:/Users/xnxs/Desktop/a.txt"))); 
			out.writeObject(test);
			
			ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File("C:/Users/xnxs/Desktop/a.txt")));
			TestSerializable t = (TestSerializable) in.readObject();
			System.out.println(t.getName());
			
			in.close();
			out.close();
			
			/**
			 * 	ObjectOutputStream 
			 * 		需要内置一个OutputStream,
			 * 		ObjectOutputStream提供的功能是: 把Object作为输出,输出在指定的输出位置
			 * 		(即将Object转换为字节,再使用内嵌的OutputStream进行输出)
			 * 		这个动作被称为序列化	
			 * ObjectInputStream 
			 * 		需要内置一个InputStream,
			 * 		ObjectInputStream提供的功能是: 将字节读出后, 再转换为Object
			 * 		(使用内嵌的InputStream读取字节, 再将字节转换为Object)
			 * 		这个动作被称为反序列化
			 * 	
			 * ObjectInputStream/ObjectOutputStream 其实也是一种另类的FilterInputStream/FilterOutputStream,
			 * 他们也只提供了一个过滤行为, 实际输出和输入行为都是有内置流实现的,
			 * 只是由于他们比较特殊, 扩展了读写的功能,而不是改变读写
			 *  
			 */
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
		
	}
}
