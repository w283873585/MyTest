package jun.learn.foundation.thread.testModuleBuild;

import java.util.concurrent.CountDownLatch;




/**
 * 线程闭锁
 * 阻塞时不释放锁
 * CountDownLatch
 * @author Administrator
 *
 */
public class TestCountDownLatch{
	public static class HorseManger extends Thread {
		private CountDownLatch readyLatch;
		private CountDownLatch beginLatch;
		
		public HorseManger(CountDownLatch readyLatch, CountDownLatch beginLatch) {
			this.readyLatch = readyLatch;
			this.beginLatch = beginLatch;
		}
		
		public void run() {
			System.out.println("比赛就要开始，请骑马手先准备");
			readyLatch.countDown();
			try {
				beginLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("比赛开始...");
		}
	}
	
	public static class Horser extends Thread {
		private CountDownLatch readyLatch;
		private CountDownLatch beginLatch;
		private final int id;
		public Horser(int id, CountDownLatch readyLatch, CountDownLatch beginLatch) {
			this.id = id;
			this.readyLatch = readyLatch;
			this.beginLatch = beginLatch;
		}
		public void run() {
			try {
				readyLatch.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("骑马手" + id + "号已经准备完毕");
			beginLatch.countDown();
		}
	}
	
	public static class Client {
		public void start() {
			CountDownLatch readyLatch = new CountDownLatch(1);
			CountDownLatch beginLatch = new CountDownLatch(10);
			HorseManger manager = new HorseManger(readyLatch, beginLatch);
			for (int i = 0; i < 10; i++) {
				new Horser(i, readyLatch, beginLatch).start();
			}
			manager.start();
		}
	}
	
	public static void main(String[] args) {
		Client client = new Client();
		client.start();
	}
	
}
