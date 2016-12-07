package jun.learn.foundation.patterns.bridge2;
public class MobileMsg extends MsgContent{

	public MobileMsg(String content) {
		this.eamilContent = content;
	}
	private String eamilContent;
	@Override
	void send() {
		System.out.println("i can send a mobileMsg: " + eamilContent);
	}

}