package jun.learn.tools.network.netty.core;

import io.netty.channel.ChannelHandlerContext;

public interface Context {
	void write(Message message);
	
	void close();
	
	boolean authorized();
	
	ChannelHandlerContext getNettyContext();
}
