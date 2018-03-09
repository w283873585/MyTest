package jun.learn.scene.thread.ABCThreadPrint;

public class ClientForTestThread2 {
	
	public static class CycleChain{
		
		private ChainObj first;
		
		private ChainObj end;
		
		public void add(Object val) {
			if (first == null) {
				first = new ChainObj(val);
				end = first;
				first.setNext(end);
			} else {
				end.setNext(new ChainObj(val));
				end = end.getNext();
				end.setNext(first);
			}
		}
		
		public Object cur() {
			return first.getVal();
		}
		
		public void next() {
			first = first.getNext();
			end = end.getNext();
		}
		
		private static class ChainObj{
			private Object val;
			private ChainObj next;
			
			public ChainObj(Object val) {
				this.val = val;
			}

			public Object getVal() {
				return val;
			}

			public ChainObj getNext() {
				return next;
			}

			public void setNext(ChainObj next) {
				this.next = next;
			}
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
				/**
				 * TODO forFuture
				 * 
				 * 场景描述, 
				 * 
				 * A,B,C线程分别负责打印10次"A","B","C"
				 * 
				 * 要求输出结果为"ABCABCABC...."
				 * 
				 */
				
				
				int i = 10;
				while (i > 0) {
					if (chain.cur() == flag) {
						i--;
						System.out.print(msg);
						Thread.sleep(100);
						
						/** 改变非原子性操作, 如果产生意想不到的结果, 可能需要同步处理   */
						chain.next();
						
						/**
						 * 循环报名, 直到轮到自己. (有点类似 CAS ?)
						 * 
						 * 状态改变居后. (当所有事情做完了, 让出发言权, 这时就算报名顺序再乱, 执行的顺序也不会乱.)
						 * 
						 */
						synchronized (chain) {}
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
		ThreadPrint cP = new ThreadPrint("C\n", C, chain);
	
		bP.start();
		aP.start();
		cP.start();
	}
}
