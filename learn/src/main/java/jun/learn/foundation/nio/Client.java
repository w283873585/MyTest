package jun.learn.foundation.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;

public class Client {
	public static void main(String[] args) throws IOException {
		SocketChannel channel = SocketChannel.open();
		
		channel.configureBlocking(false);
		channel.connect(new InetSocketAddress(8080));
		
		Selector selector = Selector.open();
		channel.register(selector, SelectionKey.OP_CONNECT);
		
		SelectionKey sKey = null;
		if (selector.select() > 0) {
			for (SelectionKey key : selector.selectedKeys()) {
				if (key.isConnectable()) {
					SocketChannel sc = (SocketChannel) key.channel();
					sc.configureBlocking(false);
					sKey = sc.register(selector, SelectionKey.OP_READ);
					sc.finishConnect();
				} else if (key.isReadable()) {
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					SocketChannel sc = (SocketChannel) key.channel();
					int readBytes = 0;
					try {
						int ret = 0;
						try {
							while ((ret = sc.read(buffer)) > 0) {
								readBytes += ret;
							}
						} finally {
							buffer.flip();
						}
					} finally {
						if (buffer != null) buffer.clear();
					}
				} else if (key.isWritable()) {
					key.interestOps(key.interestOps() & (~SelectionKey.OP_WRITE));
					SocketChannel sc = (SocketChannel) key.channel();
					int writtenedSize = sc.write(ByteBuffer.allocate(1024));
					if (writtenedSize == 0) {
						key.interestOps(key.interestOps() | SelectionKey.OP_WRITE);
					}
				}
			}
			selector.selectedKeys().clear();
		}
	}
}
