package jun.learn.tools.network.netty.core.support;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import jun.learn.tools.network.netty.core.Message;

public class MessageBuilder {
	public static ServerMessage build(String clientId) {
		return new ServerMessage(clientId);
	}
	
	public static ConnectionMessage build() {
		return new ConnectionMessage();
	}
	/**
	 * 服务端Message clientId不能为空，
	 * 否则找不到对应的连接
	 */
	public static class ServerMessage implements Message{
		private final ConnectionMessage message;
		
		public ServerMessage(String clientId) {
			message = new ConnectionMessage(clientId); 
		}
		
		@Override
		public int getType() {
			return message.getType();
		}

		@Override
		public String getClientId() {
			return message.getClientId();
		}

		@Override
		public Object get(String key) {
			return message.get(key);
		}

		@Override
		public String getBody() {
			return message.getBody();
		}
		
		public ServerMessage put(String key, Object value) {
			this.message.put(key, value);
			return this;
		}
		
		public ServerMessage wrap(Map<String, Object> map) {
			this.message.wrap(map);
			return this;
		}
		
		public ConnectionMessage getInternalMessage() {
			return this.message;
		}
	}
	
	/**
	 * Connection消息， 只需要填充内容就够了， 
	 * 因为消息type和clientId都可以在Connection中获取到
	 */
	public static class ConnectionMessage implements Message{
		private int type;
		private String clientId;
		private JSONObject body = new JSONObject();
		
		private ConnectionMessage() {}
		
		private ConnectionMessage(String clientId) {
			this.clientId = clientId;
		}
		
		private ConnectionMessage(int type, String clientId) {
			this.type = type;
			this.clientId = clientId;
		}
		
		public void setType(int type) {
			this.type = type;
		}
		
		public void setClientId(String id) {
			this.clientId = id;
		}
		
		@Override
		public int getType() {
			return type;
		}

		public ConnectionMessage put(String key, Object value) {
			this.body.put(key, value);
			return this;
		}
		
		public ConnectionMessage wrap(Map<String, Object> map) {
			body.putAll(map);
			return this;
		}
		
		@Override
		public String getClientId() {
			return clientId;
		}

		@Override
		public Object get(String key) {
			return body.get(key);
		}

		@Override
		public String getBody() {
			return body.toJSONString();
		}
	}
}
