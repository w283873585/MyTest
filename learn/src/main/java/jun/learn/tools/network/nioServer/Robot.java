package jun.learn.tools.network.nioServer;

import java.nio.channels.SocketChannel;

public interface Robot extends Runnable{
	
	public void accept(SocketChannel socket);
}
