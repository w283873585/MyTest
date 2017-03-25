package jun.learn.tools.network.netty.core.support;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Context;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;
import jun.learn.tools.network.netty.core.support.MessageType;

public class AuthMessageHandler implements MessageHandler{
	private Manager manager;
	
	public Manager getManager() {
		return manager;
	}

	@Override
	public MessageType getType() {
		return MessageType.auth;
	}

	@Override
	public void handle(Message message, Context ctx) {
		if (verify(message)) {
			getManager().addConnection(new SimpleConnection(message, ctx));
		}
	}
	
	private boolean verify(Message message) {
		if (message.getType() == 0)
			return true;
		return false;
	}
	
	/**
	 *	A Connection Object is used to send message to client initiatively
	 */
	public static class SimpleConnection implements Connection{
		private final String id;
		private Manager manager;
		private final ChannelHandlerContext ctx;
		
		public SimpleConnection(Message message, Context ctx) {
			this.id = message.getClientId();
			this.ctx = ctx.getNettyContext();
		}
		
		@Override
		public void setManager(Manager manager) {
			this.manager = manager;
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public void write(Message message) {
			ctx.writeAndFlush(message);
		}

		@Override
		public void writeAndClose(Message message) {
			ChannelFuture f = ctx.writeAndFlush(message);
			f.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					assert f == future;
					ctx.close();
				}
			});
		}

		@Override
		public void close() {
			ctx.close();
			manager.removeConnection(this);
		}
	}
}
