package jun.learn.tools.network.netty.core.support;

import java.util.HashMap;
import java.util.Map;

import jun.learn.tools.network.netty.core.Connection;
import jun.learn.tools.network.netty.core.Manager;
import jun.learn.tools.network.netty.core.Message;
import jun.learn.tools.network.netty.core.MessageHandler;

public class ManagerSupport implements Manager{
	private Map<String, Connection> connections = new HashMap<String, Connection>();
	private Map<Integer, MessageHandler> handlers = new HashMap<Integer, MessageHandler>();
	
	@Override
	public void registerHanlder(MessageHandler handler) {
		handlers.put(1, handler);
	}

	@Override
	public void addConnection(Connection conn) {
		connections.put(conn.getId(), conn);
		conn.setManager(this);
	}

	@Override
	public void dispath(Message message) {
		MessageHandler hanlder = handlers.get(message.getType());
		hanlder.handle(message);
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
	public void send(Message msg) {
		getConnection(msg.getClientId()).write(msg);
	}
	
	private Connection getConnection(String id) {
		return connections.get(id);
	}

	@Override
	public void removeConnection(Connection conn) {
		connections.remove(conn.getId());
	}
}
