package jun.learn.tools.network.netty.core.support;

import java.util.Map;

import com.alibaba.fastjson.JSONObject;

import jun.learn.tools.network.netty.core.Message;

public class MessageUtil {

	public static StanrdMessage build(MessageType type) {
		return new StanrdMessage(type.value());
	}
	
	public static ServerMessage build(MessageType type, String clientId) {
		return new ServerMessage(type.value(), clientId);
	}
	
	public static ServerMessage cloneFrom(ServerMessage message) {
		return new ServerMessage(message.getType(), message.getClientId());
	}
	
	/**
	 * 服务端Message clientId不能为空，
	 * 否则找不到对应的连接
	 */
	public static class ServerMessage implements Message{
		private final StanrdMessage message;
		
		public ServerMessage(int type, String clientId) {
			message = new StanrdMessage(type, clientId); 
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
	}
	
	/**
	 * clientId可以为空。
	 */
	public static class StanrdMessage implements Message{
		private int type;
		private String clientId;
		private JSONObject body = new JSONObject();
		
		private StanrdMessage(int type) {
			this.type = type;
		}
		
		private StanrdMessage(int type, String clientId) {
			this.type = type;
			this.clientId = clientId;
		}
		
		@Override
		public int getType() {
			return type;
		}

		public StanrdMessage put(String key, Object value) {
			this.body.put(key, value);
			return this;
		}
		
		public StanrdMessage wrap(Map<String, Object> map) {
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
	
	/**
	 * 消息类型
	 */
	
}
