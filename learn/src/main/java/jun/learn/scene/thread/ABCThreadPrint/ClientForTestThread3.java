package jun.learn.scene.thread.ABCThreadPrint;

public class ClientForTestThread3 {
	
	public static class ThreadPrint extends Thread{
		
		private String msg;
		private Object cur;
		private Object next;
		
		public ThreadPrint(String msg, Object cur, Object next) {
			this.msg = msg;
			this.cur = cur;
			this.next = next;
		}
		
		public void run() {
			for (int i = 0; i < 10; i++) {
				synchronized (cur) {
					try {
						cur.wait();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
				synchronized (next) {
					System.out.print(msg);
					next.notify();
				}
			}
		}
	}
	
	
	public static void main(String[] args) throws InterruptedException {
		Object A = new Object();
		Object B = new Object();
		Object C = new Object();
		
		
		ThreadPrint aP = new ThreadPrint("A", A, B);
		ThreadPrint bP = new ThreadPrint("B", B, C);
		ThreadPrint cP = new ThreadPrint("C", C, A);
	
		bP.start();
		aP.start();
		cP.start();
		
		Thread.sleep(110);
		synchronized (A) {
			A.notify();
		}
	}
}
