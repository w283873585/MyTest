package jun.learn.tools.network.netty.core;

import jun.learn.tools.network.netty.core.support.MessageUtil.ConnectionMessage;

public interface Connection {
	
	String getId();
	
	void write(ConnectionMessage message);
	
	void writeAndClose(ConnectionMessage message);
	
	void close();
	
	void keepAlive();
	
	boolean authorized();
}
