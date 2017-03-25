package jun.learn.tools.network.netty.core.support;

import java.util.HashMap;
import java.util.Map;

import io.netty.channel.ChannelHandlerContext;
import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Context;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;
import jun.learn.tools.network.netty.core.support.MessageType;
import jun.learn.tools.network.netty.core.support.MessageUtil.ServerMessage;

public class ManagerSupport implements Manager{
	private Map<String, Connection> connections = new HashMap<String, Connection>();
	private Map<MessageType, MessageHandler> handlers = new HashMap<MessageType, MessageHandler>();
	
	@Override
	public void registerHanlder(MessageHandler handler) {
		handlers.put(handler.getType(), handler);
	}

	@Override
	public void addConnection(Connection conn) {
		connections.put(conn.getId(), conn);
		conn.setManager(this);
	}

	/**
	 * 单类型handler
	 */
	@Override
	public void dispath(Message message, ChannelHandlerContext ctx) {
		handlers.get(MessageType.valueOf(message.getType())).handle(message,
				new SimpleContext(ctx, message, connections.get(message.getClientId()) != null));
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
		getConnection(msg.getClientId()).write(msg);
	}
	
	private Connection getConnection(String id) {
		return connections.get(id);
	}

	@Override
	public void removeConnection(Connection conn) {
		connections.remove(conn.getId());
	}
	

	public static class SimpleContext implements Context{
		private SimpleContext(ChannelHandlerContext ctx, Message message, boolean authorized) {
			this.ctx = ctx;
			this.originMsg = (ServerMessage) message;
			this.authorized = authorized;
		}
		
		private boolean authorized;
		private ChannelHandlerContext ctx;
		private ServerMessage originMsg;
		
		@Override
		public void write(Map<String, Object> body) {
			ctx.writeAndFlush(MessageUtil.cloneFrom(originMsg).wrap(body));
		}

		@Override
		public void close() {
			ctx.close();
		}
		
		@Override
		public boolean authorized() {
			return authorized;
		}

		@Override
		public ChannelHandlerContext getNettyContext() {
			return ctx;
		}
	} 
}
