package jun.learn.tools.network.netty.core.support;

import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;

public abstract class RequireAuthMessageHandler implements MessageHandler{
	
	@Override
	public void handle(Message message, Connection conn) {
		if (preHandle(message, conn))
			doHandle(message, conn);
	}
	
	public boolean preHandle(Message message, Connection conn) {
		if (conn.authorized())
			return true;
		
		/**
		 * doSomething here, 
		 * for example: reply some warnning info.
		 */
		return false;
	}
	
	@Override
	public abstract MessageType getType();
	
	public abstract void doHandle(Message message, Connection conn);
}
