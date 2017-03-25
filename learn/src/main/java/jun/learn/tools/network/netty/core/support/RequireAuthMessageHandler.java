package jun.learn.tools.network.netty.core.support;

import jun.learn.tools.network.netty.core.Context;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;

public abstract class RequireAuthMessageHandler implements MessageHandler{
	
	@Override
	public void handle(Message message, Context ctx) {
		if (preHandle(message, ctx))
			doHandle(message, ctx);
	}
	
	public boolean preHandle(Message message, Context ctx) {
		if (ctx.authorized())
			return true;
		
		/**
		 * doSomething here, 
		 * for example: reply some warnning info.
		 */
		return false;
	}
	
	@Override
	public abstract MessageType getType();
	
	public abstract void doHandle(Message message, Context ctx);
}
