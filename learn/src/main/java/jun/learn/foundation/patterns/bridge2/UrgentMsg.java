package jun.learn.foundation.patterns.bridge2;

public class UrgentMsg extends MsgSend{
	private int timeout;
	public UrgentMsg(MsgContent msg, int timeout) {
		super(msg);
		this.timeout = timeout;
	}
	
	@Override
	void send() {
		if (timeout < 1000) {
			super.getMsg().send();
		} else {
			throw new RuntimeException("you can send a timout msg");
		}
	}
}
