package jun.learn.tools.network.netty.core;

public interface Message {
	
	/**
	 * 消息类型
	 */
	public int getType();
	
	/**
	 * 客户端Id
	 */
	public String getClientId();
	
	/**
	 * 通过key获取值
	 */
	public Object get(String key);
}
