package jun.learn.foundation.thread.testModuleBuild;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;





/**
 * 线程信号量
 * Semaphore
 * 阻塞时不释放锁
 * @author Administrator
 *
 */
public class TestSemaphore<T>{
	private final Set<T> set;
	private final Semaphore sem;
	
	public TestSemaphore(int bound) {
		this.set = Collections.synchronizedSet(new HashSet<T>());
		this.sem = new Semaphore(bound);
	}
	
	public synchronized boolean add(T o) throws InterruptedException {
		sem.acquire();
		boolean wasAdded = false;
		try {
			wasAdded = set.add(o);
			return wasAdded;
		} finally {
			if (!wasAdded) {
				sem.release();
			}
		}
	}
	
	public synchronized boolean remove(Object o) {
		boolean wasRemoved = set.remove(o);
		if (wasRemoved) {
			sem.release();
		}
		return wasRemoved;
	}
	
	public static void main(String[] args) {
		/**
		 * 场景描述，给定一个大小为1的信号量，
		 * 在线程1加入两个元素，让线��阻塞，但是此时线程1获取了对象锁，如果第二个元素加入成功则最后打印一个success
		 * 在线程2删除其中一个元素，让线程1不再阻塞，但是失败了，产生死锁了，线程1获取锁后就再也没有释放锁了，而这个线程就一直在等待锁。
		 * 信号量阻塞线程时，不会放弃所持有的锁。所以设计的时候，要小心产生死锁。
		 */
		final TestSemaphore<String> test = new TestSemaphore<String>(1);
		new Thread(new Runnable() {
			public void run() {
				try {
					test.add("123132");
					test.add("11312");
					System.out.println("success");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		new Thread(new Runnable() {
			public void run() {
				try {
					Thread.sleep(100);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				test.remove("123132");
			}
		}).start();
	}
}
