package learn.pattern.dutyChain;

import learn.pattern.dutyChain.ChainTwo.Node;

public class Client {
	public static void main(String[] args) {
		Object arg = new Object();
		
		// 链表实现的责任链, 组装麻烦, 但请求传递比较自然
		ChainOne one = new ChainOne();
		ChainOne one1 = new ChainOne();
		one.setNext(one1);
		ChainOne one2 = new ChainOne();
		one1.setNext(one2);
		ChainOne one3 = new ChainOne();
		one2.setNext(one3);
		one.invoke(arg);
		
		// 队列实现的责任链, 组装轻便, 请求传递繁琐
		ChainTwo two = new ChainTwo();
		two.addNode(new Node());
		two.addNode(new Node());
		two.addNode(new Node());
		two.addNode(new Node());
		two.invoke(arg);
		
	}
}
