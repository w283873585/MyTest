package jun.learn.foundation.thread.testBuildSyncUtil;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import jun.learn.foundation.thread.util.GuardedBy;
import jun.learn.foundation.thread.util.ThreadSafe;
// 单次通知
@ThreadSafe
public class ConditionBoundedBuffer<T> {
	protected final Lock lock = new ReentrantLock();
	// 条件谓词 非满
	private final Condition notFull = lock.newCondition();
	// 条件谓词 非空
	private final Condition notEmpty = lock.newCondition();
	@SuppressWarnings("unchecked")
	@GuardedBy("lock")
	private final T[] items = (T[]) new Object[1024];
	@GuardedBy("lock")
	private int tail, head, count;
	
	public void put(T x) throws InterruptedException {
		lock.lock();
		try {
			while (count == items.length) {
				notFull.await();
			}
			items[tail] = x;
			if (++tail == items.length) {
				tail = 0;
			}
			++count;
			notEmpty.signal();
		} finally {
			lock.unlock();
		}
	}
	
	
	public T take() throws InterruptedException {
		lock.lock();
		try {
			while (count == 0) {
				notEmpty.await();
			}
			T x = items[head];
			if (++head == items.length) {
				head = 0;
			}
			--count;
			notFull.signal();
			return x;
		} finally {
			lock.unlock();
		}
	}
}
