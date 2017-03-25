package jun.learn.tools.network.netty.core;

import java.util.Map;

import io.netty.channel.ChannelHandlerContext;

public interface Context {
	void write(Map<String, Object> body);
	
	void close();
	
	boolean authorized();
	
	ChannelHandlerContext getNettyContext();
}
