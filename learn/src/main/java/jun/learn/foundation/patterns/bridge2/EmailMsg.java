package jun.learn.foundation.patterns.bridge2;

public class EmailMsg extends MsgContent{

	public EmailMsg(String content) {
		this.eamilContent = content;
	}
	private String eamilContent;
	@Override
	void send() {
		System.out.println("i can send a email: " + eamilContent);
	}

}
