package jun.learn.tools.network.netty.core;

public interface Message {
	/**
	 * 消息类型
	 */
	public int getType();
	
	/**
	 * 客户端Id,  由netty生成的唯一标识channel的一个特殊id
	 */
	public String getClientId();
	
	/**
	 * 通过主体内容中key对应的值
	 */
	public Object get(String key);
	
	/**
	 * 获取所有主体内容
	 */
	public String getBody();
}
