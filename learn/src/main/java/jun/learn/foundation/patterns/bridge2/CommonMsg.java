package jun.learn.foundation.patterns.bridge2;

public class CommonMsg extends MsgSend{
	public CommonMsg(MsgContent msg, int timeout) {
		super(msg);
	}
	
	@Override
	void send() {
		super.getMsg().send();
	}
}
