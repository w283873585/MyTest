package jun.learn.tools.network.nioServer2;

import java.io.IOException;
import java.nio.channels.SelectionKey;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class ServerHandler {
	private Robot robot = new Robot(); 
	
	public void handle(SelectionKey key) throws IOException {
		if (key.isAcceptable()) 
			handleAccept(key);
		
		if (key.isReadable())
			handleRead(key);
	}
	
	private void handleAccept(SelectionKey key) throws IOException {
		ServerSocketChannel server = (ServerSocketChannel) key.channel();
    	SocketChannel channel = server.accept();
    	channel.configureBlocking(false);
    	if (channel != null)
    		channel.register(key.selector(), SelectionKey.OP_READ);
	}
	
	private void handleRead(SelectionKey key) throws IOException {
		robot.recvAndReply(key);
	}
}