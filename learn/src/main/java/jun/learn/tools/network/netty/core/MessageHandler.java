package jun.learn.tools.network.netty.core;

import jun.learn.tools.network.netty.core.support.MessageType;

public interface MessageHandler {
	
	public void handle(Message message, Connection ctx);
	
	public MessageType getType();
}
