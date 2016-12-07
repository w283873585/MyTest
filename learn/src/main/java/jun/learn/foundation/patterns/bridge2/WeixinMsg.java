package jun.learn.foundation.patterns.bridge2;

public class WeixinMsg extends MsgContent{
	private String content;
	
	public WeixinMsg(String content) {
		this.content = content;
	}
	@Override
	void send() {
		System.out.println("weixin message: " + content);
	}
}
