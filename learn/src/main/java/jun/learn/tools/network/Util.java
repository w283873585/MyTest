package jun.learn.tools.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Util {
	public static final String SHUTDOWN = "886";
	
	public static boolean isShutdown(String command) {
		return SHUTDOWN.equals(command);
	}
	
	public static void sendShutdownCommand(OutputStream out) throws IOException {
		write(out, SHUTDOWN);
	}
	
	public static void sendShutdownCommand(SocketChannel out) throws IOException {
		write(out, SHUTDOWN);
	}
	
	public static byte[] int2byte(int res) {
		byte[] targets = new byte[4];  
		targets[3] = (byte) (res & 0xff);			// 最低位   
		targets[2] = (byte) ((res >> 8) & 0xff);	// 次低位   
		targets[1] = (byte) ((res >> 16) & 0xff);	// 次高位   
		targets[0] = (byte) (res >>> 24);			// 最高位,无符号右移。   
		return targets;   
	}
	
	public static int byte2Int(byte[] b) {
        int intValue = 0;
        for (int i = 0; i < b.length; i++) {
            intValue += (b[i] & 0xFF) << (8 * (3 - i));
        }
        return intValue;
    }
	
	/**
	 *	从流中读取一个包， 包头只有4个字节, 用来标记包内容的大小
	 *	如果不存在一个完整包， 则阻塞
	 */		
	public static String read(InputStream input) throws IOException {
		StringBuilder sb = new StringBuilder();
		byte buff[] = new byte[1024];
		
		/**
		 * 每个数据报的前4个字节用来表示数据包的长度
		 */
		byte[] head = new byte[4];
		int hasReaded = 0;
		while (hasReaded < 4)
			hasReaded += input.read(head, hasReaded, 4 - hasReaded);
		
		/**
		 * 获取真实数据
		 */
		int surplus = byte2Int(head);
		while (surplus > 0) {
			int length = input.read(buff, 0, surplus > 1024 ? 1024 : surplus);
			surplus -= length;
			sb.append(new String(buff, 0, length));
		}
		return sb.toString();
	}
	
	/**
	 * 输出内容时， 给包添加一个4字节的包头， 用来标记包主体的大小
	 */
	public static void write(OutputStream out, String content) throws IOException {
		out.write(int2byte(content.getBytes().length));
		out.write(content.getBytes());
		out.flush();
	}
	
	/**
	 * 输出内容时， 给包添加一个4字节的包头， 用来标记包主体的大小
	 */
	public static void write(SocketChannel out, String content) throws IOException {
		ByteBuffer buff = ByteBuffer.allocate(1024);
		byte[] bContent = content.getBytes();
		buff.putInt(bContent.length);
		int offset = 0, 
			length = bContent.length,
			limit = length > 1020 ? 1020 : length;
		
		while (offset < limit) {
			buff.put(bContent, offset, limit);
			out.write(buff);
			buff.clear();
			offset = limit;
			limit = (offset + 1024 > length) ? length : offset + 1024;
		}
	}
	
	
	public static String read(SocketChannel channel) throws IOException {
		StringBuilder sb = new StringBuilder();
		ByteBuffer buff = ByteBuffer.allocate(4);
		while (buff.hasRemaining())
			channel.read(buff);
		
		buff.flip();
		buff = ByteBuffer.allocate(buff.getInt());
		while (buff.hasRemaining()) {
			channel.read(buff);
			buff.flip();
			while (buff.hasRemaining())
				sb.append(buff.getChar());
		}
		return sb.toString();
	}
	
	public static void main(String[] args) throws IOException {
		System.out.println(byte2Int(int2byte(5)));
	}	
}
