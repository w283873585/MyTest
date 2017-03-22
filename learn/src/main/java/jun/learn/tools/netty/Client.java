package jun.learn.tools.netty;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

public class Client {
	public static void main(String[] args) {
		try (SocketChannel channel = SocketChannel.open()) {
			channel.connect(new InetSocketAddress(8088));
			ByteBuffer buf = ByteBuffer.allocate(1024);
			buf.put("hello world".getBytes());
			buf.flip();
			while(buf.hasRemaining()) {
			    channel.write(buf);
			}
			
			buf.clear();
			while (channel.read(buf) != -1) {
				buf.flip();
				if (buf.hasRemaining())
					System.out.println(Charset.forName("utf-8").decode(buf));
				buf.clear();
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
