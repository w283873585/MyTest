package jun.learn.foundation.patterns.bridge2;

public abstract class MsgSend {
	private MsgContent msg;
	public MsgSend(MsgContent msg) {
		this.msg = msg;
	}
	
	public MsgContent getMsg() {
		return this.msg;
	}
	
	abstract void send();
}
