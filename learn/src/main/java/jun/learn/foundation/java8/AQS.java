package jun.learn.foundation.java8;

import java.util.Scanner;
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
		
		/**
		 * 多线程调试
		 * 
		 * 首先获取互斥锁的人，会独占锁，直到调用release方法，
		 * （
		 * 	该行为在AQS的内部表现为  compareAndSetState(1) -> setState(0), 
		 * 	即更改AQS的state属性，为0时则相当于放弃占有权
		 * ） 
		 * 最后再查看AQS的队列，查看是否有等待的线程，如果有则唤醒首部的线程
		 * 
		 * 在互斥锁被别人占有时，进来的线程会被存储在AQS的队列中，阻塞等待唤醒。
		 * 
		 * 
		 * head节点的作用：
		 * 
		 * waitStatus：
		 * 		CANCELLED： 1	中断，直接抛弃
		 * 		SIGNAL： -1		等待唤醒
		 * 		CONDITION： -2	
		 * 		PROPAGATE： -3
		 */
		ThreadUtil.exec(AQS::exec);
		ThreadUtil.exec(AQS::exec);
		ThreadUtil.exec(() -> {
			Thread thread = Thread.currentThread();
			ThreadUtil.exec(() -> {
				Scanner sc = new Scanner(System.in);   
		        System.out.println("输入第一个boolean值(true/false):");  
		        if (sc.nextBoolean()) {  
		        	thread.interrupt();
		        } 
		        sc.close();
			});
			try {
				lock.lockInterruptibly();
				System.out.println("obtain lock success!");
			} catch (InterruptedException e) {
				System.out.println("haha i am abort!");
			}
		});
		/**
		 * 	AQS重点：
		 * 		维护线程队列，
		 * 		获取(acquire), 释放(release)
		 * 		悬停等待		LockSupport.park(thread)
		 * 		独占模式 和 共享模式
		 * 
		 * 	核心是用CAS无阻塞算法替代多线程调度，减少开销，提高性能。 
		 */
		
		
		/**
		 * 	ReentrantLock:
		 * 		lock()		-->   获取锁，未获取则悬停，传递中断信号
		 * 		lockInterruptibly()	-->   获取锁，未获取则悬停，传递中断异常
		 * 		tryLock()	-->   试图获取锁，未获取则返回false
		 * 		unlock()	-->	    释放锁
		 * 
		 * 	AQS:
		 * 		模板模式实现
		 * 		
		 * 		acquire
		 * 
		 * 		release
		 */
	}
	
	private static final Lock lock = new ReentrantLock();
	public static void exec() {
		try {
			lock.lock();
			System.out.println("i come here" + Thread.currentThread().getName());
		} finally {
			lock.unlock();
		}
	}
	
/**
	首先AQS(AbstractQueuedSynchronizer)是一个抽象模板。
	简单用代码来描述下aqs结构：
	var aqs = {
		state: 0,
		
		head: null,
		tail: null,
		
		acquire: function(val) {},
		release: function(val) {},
	}
	
	从结构其实就能看出aqs大概是个什么东西
	首先他有个state状态，（这个state代表线程对资源的掌控状态）
	head和tail是个引用，分别指向一个队列的头与尾 (用来维护一个线程队列)
	然后就是两个核心方法acquire与release
	
	aqs作为一个Synchronizer， 他肯定是为多线程服务的。
	说白了，它就是一个维护"线程队列"的容器。
	
	tryAcquire与tryRelease作为
		真正获取状态与释放状态的实现方法
		让子类通过setState和compareAndSetState实现
	
	aqs的线程安全是由CAS实现的。
	
	ReentrantLock是通过一个独占模式的aqs实现的。
*/
}
