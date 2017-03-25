package jun.learn.tools.network.netty.core.support;
public enum MessageType{
	auth(0);
	
	private final int value;
	
	private MessageType(int value) {
		this.value = value;
	}
	
	public int value() {
		return this.value;
	}
	
	public static MessageType valueOf(int value) {
		for (MessageType type : MessageType.values()) {
			if (type.value() == value)
				return type;
		}
		return null;
	}
}