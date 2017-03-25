package jun.learn.tools.network.netty.core.support;

import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;

public class AuthMessageHandler implements MessageHandler{
	
	@Override
	public MessageType getType() {
		return MessageType.auth;
	}

	@Override
	public void handle(Message message, Connection ctx) {
		if (verify(message))
			ctx.keepAlive();
	}
	
	private boolean verify(Message message) {
		if (message.getType() == 0)
			return true;
		return false;
	}
}
