package learn.atest.ABCThreadPrint;

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
					if (chain.cur() == flag) {
						i--;
						System.out.print(msg);
						Thread.sleep(100);
						chain.next();
						synchronized (chain) {
						}
					}
					Thread.sleep(1);
				}
			} catch (InterruptedException e) {}
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
		ThreadPrint cP = new ThreadPrint("C", C, chain);
	
		bP.start();
		aP.start();
		cP.start();
	}
}
