package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Iterator;

public class Server {
	
	public void start() {
		/**
		 *	推送消息给客户端,
		 *  接收客户端的消息 
		 */
		try {
			boolean shutdown = false;
			
			ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
			serverSocketChannel.bind(new InetSocketAddress(12020));
			serverSocketChannel.configureBlocking(false);
			
			ServerHandler handler = new ServerHandler();
			Selector selector = Selector.open();
			serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
			while (!shutdown) {
				if (selector.select() == 0) continue;
				Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
				while (keyIterator.hasNext()) {
					handler.handle(keyIterator.next());
					keyIterator.remove();
				}
			}
			serverSocketChannel.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String[] args){
		new Server().start(); 
	}
}
