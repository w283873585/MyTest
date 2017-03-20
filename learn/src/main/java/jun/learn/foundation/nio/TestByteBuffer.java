package jun.learn.foundation.nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 *	测试使用ByteBuff
 *	并简单描述其用法。
 */
public class TestByteBuffer {
	@SuppressWarnings("resource")
	public static void main(String[] args) throws IOException {
//		System.getProperty("user.dir") 			         获取当前项目路径
//		TestBuffer.class.getResource("").getPath()  获取绝对路径
		File file = new File(TestByteBuffer.class.getResource("").getPath() + "tian.txt");
		FileChannel channel =  new FileInputStream(file).getChannel();

// 		分配一个buff
//		ByteBuffer buff = ByteBuffer.allocate(1024);

// 		另一种方式  wrap
//		包装的byte数组会根据buff数据的变化而变化
// 		buff.array() 会返回该byte数组	即： arr == buff.array() --> true
		byte[] arr = new byte[1024];
		ByteBuffer buff = ByteBuffer.wrap(arr);
		
		int i = 0;
		while (i != -1) {
			i = channel.read(buff);
			output(buff);
		}
		channel.close();
	}
	private static void output(ByteBuffer buff) throws UnsupportedEncodingException {
		/**
		 *  ByteBuffer初始化时, position = 0, limit = capacity.
		 *  
		 *  position 
		 *  	作为位置指针, 读入和写出都得通过移动该指针实现
		 *  limit	 
		 *  	 限定指针, 作为可读的最大空间, 以及可写的内容上限.
		 *  put, write, 
		 *  	会让position偏移, 并将数据填充在ByteBuffer内部的byte数组. 
		 *  	使用flip切换, 方便读取刚刚填充的数据.
		 *  get, read, 
		 *  	同样会让postion偏移, 并将数据从ByteBuffer读出(并没有真正取出, 可以通过rewind复读),
		 *  	使用flip切换, 方便重新写入新的数据.
		 *  
		 *  切换模式: 
		 *  	flip 
		 *  		模式切换(其实没有模式这一说，只是抽象的说法，bytebuffer的读写行为完全取决于position与limit)
		 *  		limit = position, position = 0
		 *  	compact 
		 *  		将读模式改为写模式，且保留未写出的数据
		 *  
		 *  clear
		 *  	初始化, 清楚所有状态(并没有清除内部的数据): position = 0, limit = capacity; // 
		 */
		buff.flip();
		byte[] box = new byte[buff.remaining()];
		if (buff.hasRemaining()) {
			buff.get(box);
			System.out.print(new String(box, "gbk"));
		}
		buff.clear();
		
		/**
		buff。mark();	记录位置
		buff.reset();	恢复记录的位置
		buff。rewind();	在读模式下，将position设置为0，limit不变，即用来重读数据
		
		buff.remaining() 
			读模式返回还需要读取的长度，
			写模式返回还可以写的长度
			其实就是返回 limit - position
		*/
	}
}
