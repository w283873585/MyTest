package jun.learn.scene.thread.ABCThreadPrint;

import java.util.ArrayList;
import java.util.List;

public class ClientForTestThread {
	
	public static class CycleChain{
		private int index = 0;
		private List<Object> chain = new ArrayList<Object>();
		
		public void add(Object obj) {
			chain.add(obj);
		}
		
		public Object cur() {
			return chain.get(index % chain.size());
		}
		
		public void next() {
			index++;
		}
	}
	
	public static class ThreadPrint extends Thread{
		
		private String msg;
		private Object flag;
		private CycleChain chain;
		
		public ThreadPrint(String msg, Object flag, CycleChain chain) {
			this.msg = msg;
			this.flag = flag;
			this.chain = chain;
		}
		
		public void run() {
			try {
				int i = 10;
				while (i > 0) {
					/**
					 * 场景描述：
					 * 		1. 一个共享的"循环标记生产机"
					 * 		2. 独立的线程打印机，并将标记注册在"循环标记生产机"中
					 * 		3. 线程打印机每次都去"循环标记生产机"中去获取标记，如果获取的标记是自己的，则打印此标记
					 */
					
					
					/**
					 * method 1
					 * 		打印机线程改变共享对象时，加锁， 并直接打印出自身印记
					 * 		防止改变对象后其他对象优先打印
					 */
					/*if (chain.cur() == flag) {
						i--;
						synchronized (chain) {
							chain.next();
							System.out.print(msg);
						}
						Thread.sleep(100);
					}
					Thread.sleep(1);*/
					
					
					/**
					 * method 2
					 * 		锁的目标有问题，没有达到线程隔离的效果
					 */
					/*if (chain.cur() == flag) {
						i--;
						synchronized (this) {
							chain.next();
							System.out.print(msg);
						}
						Thread.sleep(100);
					}
					Thread.sleep(1);*/
					
					
					/**
					 * method 3
					 * 		优先打印，最后改变共享对象。
					 * 		由于打印机是循环取值，当共享对象改变后下一个正确的打印机就会正确的打印
					 *//*
					if (chain.cur() == flag) {
						i--;
						System.out.print(msg);
						chain.next();
						Thread.sleep(100);
					}
					Thread.sleep(1);*/
					
					
					/**
					 * method 4
					 *		优先改变共享值，不能保证能优先打印
					 *		所以顺序很大可能会有问题
					 */
					if (chain.cur() == flag) {
						i--;
						chain.next();
						System.out.print(msg);
						Thread.sleep(100);
					}
					Thread.sleep(1);
					
				}
			} catch (Exception e) {}
		}
	}
	
	
	public static void main(String[] args) {
		Object A = new Object();
		Object B = new Object();
		Object C = new Object();
		
		CycleChain chain = new CycleChain();
		chain.add(A);
		chain.add(B);
		chain.add(C);
		
		ThreadPrint aP = new ThreadPrint("A", A, chain);
		ThreadPrint bP = new ThreadPrint("B", B, chain);
		ThreadPrint cP = new ThreadPrint("C\n", C, chain);
	
		bP.start();
		aP.start();
		cP.start();
	}
}
