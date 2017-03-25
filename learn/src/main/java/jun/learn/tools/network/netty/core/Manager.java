package jun.learn.tools.network.netty.core;

import java.io.Closeable;

import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.support.MessageBuilder.ServerMessage;

public interface Manager extends Closeable{
	
	/**
	 * obtain Message from the Netty, 
	 * then transport it to specify MessageHandler
	 */
	public void dispath(Message message, ChannelHandlerContext ctx);
	
	/**
	 * 	shutdown one specify connection
	 */
	public void shutdown(String clientId);
	
	/**
	 *  send the Message to the client 
	 *  by the Connection
	 */
	public void send(ServerMessage msg);
	
	/**
	 *  close the whole connections
	 */
	public void close();
}
