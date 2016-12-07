package jun.learn.foundation.thread.testModuleBuild;



/**
 * 线程中断
 * @author Administrator
 *
 */
public class TestInterrupt{
	
	public static class Client1 extends Thread{
		public void run() {
			/*
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				System.out.println("current thread is interrupted");
				Thread.currentThread().interrupt();
			}
			*/
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("firrejwaojfoijaewoifejoijwe");
			System.out.println("fjaoijfeojfoi");
		}
	}
	
	
	public static class Client2 extends Thread{
		private final Thread thread;
		public Client2(Thread thread) {
			this.thread = thread;
		}
		public void run() {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			try {
				// 阻塞当前线程，等待这个线程执行完
				thread.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("---------------------------");
			// 只会抛出一个中断信号，并不会真正中断线程，
			// 需要线程自己配合改中断信号，
			// 还有这个操作可以让在唤醒某些阻塞状态的线程（如sleep, wait, 被join的线程）
			thread.interrupt();
		}
	}
	
	
	public static void main(String[] args) {
		Client1 c1 = new Client1();
		Client2 c2 = new Client2(c1);
		c1.start();
		c2.start();
	}
}
