package jun.learn.foundation.patterns.bridge2;

public class Client {
	public static void main(String[] args) {
		MsgContent msg = new EmailMsg("武林大会就要�?���?谢�?已经被抓�?无忌你在�?");
		MsgContent msg1 = new MobileMsg("武林大会就要�?���?谢�?已经被抓�?无忌你在�?");
		MsgContent msg2 = new WeixinMsg("武林大会就要�?���?谢�?已经被抓�?无忌你在�?");
		
		
		// 这是�?��加�?信息
		
		MsgSend sender = new UrgentMsg(msg, 999);
		MsgSend sender1 = new UrgentMsg(msg1, 999);
		MsgSend sender2 = new UrgentMsg(msg2, 999);
		
		sender.send();
		sender1.send();
		sender2.send();
		
	}
	
	
	/*
	 * 桥接模式定义: 将抽象部分和它的实现部分分离，使他们都可以独立的变化
	 * 
	 * 在软件系统中，某些类型由于自身的逻辑，它具有两个或两个以上的维度变化，那么如何应对这种多维度的变化
	 * 何利用面向对象的组合来使得该类型能够轻松的沿多个方向进行变化，而又不引入额外的复杂度呢？这就是即将要介绍的桥接模式（Bridge）
	 * 
	 */
}
