package jun.learn.tools.network.netty.core;

import java.io.Closeable;

public interface Manager extends Closeable{
	
	public void registerHanlder(MessageHandler handler);
	
	public void addConnection(Connection conn);
	
	/**
	 * obtain Message from the Netty, 
	 * then transport it to specify MessageHandler
	 * 
	 * AuthMessageHandler
	 */
	public void dispath(Message message);
	
	/**
	 * 	shutdown one specify connection
	 */
	public void shutdown(String clientId);
	
	/**
	 *  send the Message to the client 
	 *  by the Connection
	 */
	public void send(Message conent);
	
	/**
	 * 
	 */
	public void close();
}
