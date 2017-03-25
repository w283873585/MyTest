package jun.learn.tools.network.netty.core;

import jun.learn.tools.network.netty.core.support.MessageType;

public interface MessageHandler {
	
	public void handle(Message message, Context ctx);
	
	public MessageType getType();
}
