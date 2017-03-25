package jun.learn.tools.network.netty.core;

import java.io.Closeable;

import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.support.MessageUtil.ServerMessage;

public interface Manager extends Closeable{
	
	public void registerHanlder(MessageHandler handler);
	
	public void addConnection(Connection conn);
	
	public void removeConnection(Connection conn);
	
	/**
	 * obtain Message from the Netty, 
	 * then transport it to specify MessageHandler
	 * 
	 * AuthMessageHandler
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
	public void send(ServerMessage conent);
	
	/**
	 * 
	 */
	public void close();
}
