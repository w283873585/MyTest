package jun.learn.tools.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class Client {
	public static void main(String[] args) {
		try (SocketChannel channel = SocketChannel.open()) {
			channel.connect(new InetSocketAddress("192.168.200.74", 8088));
			
			ByteBuffer buf = ByteBuffer.allocate(1024);
			/*
			buf.put("hello world".getBytes());
			
			buf.flip();
			while(buf.hasRemaining()) {
			    channel.write(buf);
			}
			*/
			
			buf.clear();
			while (channel.read(buf) != -1) {
				buf.flip();
				while (buf.hasRemaining())
					System.out.print(buf.getInt());
				buf.clear();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
