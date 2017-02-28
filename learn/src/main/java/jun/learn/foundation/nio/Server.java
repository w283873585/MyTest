package jun.learn.foundation.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;


public class Server {
	public static void main(String[] args) throws IOException {
		
		ServerSocketChannel server = ServerSocketChannel.open();
		ServerSocket serverSocket = server.socket();
		
		serverSocket.bind(new InetSocketAddress(8080));
		server.configureBlocking(false);
		
		Selector selector = Selector.open();
		server.register(selector, SelectionKey.OP_ACCEPT);
		if (selector.select() > 0) {
			Set<SelectionKey> keys = selector.selectedKeys();
			for (SelectionKey key : keys) {
				if (key.isAcceptable()) {
					ServerSocketChannel serverChannel = (ServerSocketChannel) key.channel();
					SocketChannel sc = serverChannel.accept();
					if (sc == null) continue;
					sc.configureBlocking(false);
					sc.register(selector, SelectionKey.OP_READ);
				}
			}
		}
	}
}
