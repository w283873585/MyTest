package jun.learn.foundation.java8;

import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AQS {
	public interface Counter {
		public void increment();
		public void print();
	}
	
	public static class Counter_lock implements Counter{
		private int value = 0;
		
		/**
		 * 其实就是维护一个队列, 将线程放入队列, 用阻塞确保延迟执行
		 */
		private Lock lock = new ReentrantLock();
		
		public void increment() {
			try {
				lock.lock();
				value++;
			} finally {
				lock.unlock();
			}
		}
		public void print() {
			System.out.println("Counter_lock       value -->" + value);
		}
	}
	
	public static class Counter_lock_wrong implements Counter{
		private int value = 0;
		public void increment() {
			/**
			 * 锁只有在共享的时候才可以生效, 
			 * 在这里, 每个线程会持有一个锁, 
			 */
			Lock lock = new ReentrantLock();
			try {
				lock.lock();
				value++;
			} finally {
				lock.unlock();
			}
		}
		public void print() {
			System.out.println("Counter_lock_wrong value -->" + value);
		}
	}
	
	public static class Counter_sync implements Counter{
		private int value = 0;
		public synchronized void increment() {
			value++;
		}
		public void print() {
			System.out.println("Counter_sync       value -->" + value);
		}
	}
	
	public static class Counter_atomic implements Counter{
		private AtomicInteger value = new AtomicInteger();
		public synchronized void increment() {
			// 线程安全委托 --> 不变性条件
			value.incrementAndGet();
		}
		public void print() {
			System.out.println("Counter_atomic     value -->" + value);
		}
	}
	
	/**
	 * 多线程环境下执行counter.increment()
	 * 在所有线程都执行完时, 打印出最终的结果, 
	 */
	public static void test(Counter counter) {
		Semaphore semaphore = new Semaphore(-99);
		
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					counter.increment();
					semaphore.release();
				}
			}).start();
		}
		
		try {
			semaphore.acquire();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		counter.print();
	}
	
	public static void main(String[] args) throws InterruptedException {
		test(new Counter_lock());
		test(new Counter_lock_wrong());
		test(new Counter_sync());
		test(new Counter_atomic());
	}
}
