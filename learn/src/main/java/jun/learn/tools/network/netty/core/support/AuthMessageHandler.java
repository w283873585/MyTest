package jun.learn.tools.network.netty.core.support;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;

public class AuthMessageHandler implements MessageHandler{
	private static final String CONTEXTKEY = "CONTEXTKEY";

	private Manager manager;
	
	public Manager getManager() {
		return manager;
	}

	@Override
	public void handle(Message message) {
		if (verify(message)) {
			getManager().addConnection(	new MyConnection(message));
		}
	}
	
	private boolean verify(Message message) {
		return true;
	}
	
	/**
	 *	A Connection Object is used to send message to client initiatively
	 */
	public static class MyConnection implements Connection{
		private final String id;
		private Manager manager;
		private final ChannelHandlerContext ctx;
		
		public MyConnection(Message message) {
			this.id = message.getClientId();
			this.ctx = (ChannelHandlerContext) message.get(CONTEXTKEY);
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
