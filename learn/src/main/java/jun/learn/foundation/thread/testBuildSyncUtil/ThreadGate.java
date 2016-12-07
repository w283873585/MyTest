package jun.learn.foundation.thread.testBuildSyncUtil;

import jun.learn.foundation.thread.util.GuardedBy;
import jun.learn.foundation.thread.util.ThreadSafe;

@ThreadSafe
public class ThreadGate {
	@GuardedBy("this") private boolean isOpen;
	@GuardedBy("this") private int generation;
	
	public synchronized void close() {
		isOpen = false;
	}
	
	public synchronized void open() {
		++generation;
		isOpen = true;
		notifyAll();
	}
	
	public synchronized void await() throws InterruptedException {
		int arrivalGenration = generation;
		while (!isOpen && arrivalGenration == generation) {
			wait();
		}
	}
	
	public static class Client{
		public static void main(String[] args) throws InterruptedException {
			final ThreadGate t = new ThreadGate();
			Thread th = new Thread() {
				public void run() {
					Hello h = new Hello(t);
					h.sleep();
					System.out.println("i am wake up");
				}
			};
			th.start();
			Thread.sleep(3000);
			t.open();
		}
	}
	public static class Hello{
		ThreadGate t;
		public Hello(ThreadGate t) {
			this.t = t;
		}
		public void sleep() {
			try {
				t.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
