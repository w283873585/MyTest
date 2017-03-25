package jun.learn.tools.network.netty.core.support;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;
import jun.learn.tools.network.netty.core.support.MessageType;
import jun.learn.tools.network.netty.core.support.MessageUtil.ConnectionMessage;
import jun.learn.tools.network.netty.core.support.MessageUtil.ServerMessage;

public class ManagerSupport implements Manager{
	private Map<String, Connection> connections = new HashMap<String, Connection>();
	private Map<MessageType, MessageHandler> handlers = new HashMap<MessageType, MessageHandler>();
	
	/**
	 * 单类型handler
	 */
	@Override
	public void dispath(Message message, ChannelHandlerContext ctx) {
		Connection conn = getConnection(message.getClientId());
		if (conn == null)
			conn = createConnection(message, ctx);
		handlers.get(MessageType.valueOf(message.getType())).handle(message, conn);
	}

	@Override
	public void close() {
		connections.forEach((String clientId, Connection conn) -> {
			conn.close();
		});
	}

	@Override
	public void shutdown(String id) {
		getConnection(id).close();
	}

	@Override
	public void send(ServerMessage msg) {
		getConnection(msg.getClientId()).write(msg.getInternalMessage());
	}
	
	private Connection createConnection(Message msg, ChannelHandlerContext ctx) {
		return new SimpleConnection(msg, ctx);
	}
	
	private Connection getConnection(String id) {
		return connections.get(id);
	}

	/**
	 *	A Connection Object is used to send message to client initiatively
	 */
	private class SimpleConnection implements Connection{
		private final String id;
		private final int type;
		private boolean authorized;
		private final ChannelHandlerContext ctx;
		
		private SimpleConnection(Message msg, ChannelHandlerContext ctx) {
			this.id = msg.getClientId();
			this.type = msg.getType();
			this.ctx = ctx;
		}
		
		@Override
		public String getId() {
			return id;
		}

		@Override
		public void write(ConnectionMessage message) {
			doWrite(message);
		}
		
		private ChannelFuture doWrite(ConnectionMessage message) {
			message.setClientId(id);
			message.setType(type);
			return ctx.writeAndFlush(message);
		}

		@Override
		public void writeAndClose(ConnectionMessage message) {
			ChannelFuture f = doWrite(message);
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
			connections.remove(this.getId());
			ctx.close();
		}

		@Override
		public void keepAlive() {
			this.authorized = true;
			connections.put(this.getId(), this);
		}

		@Override
		public boolean authorized() {
			return authorized;
		}
	}
}
