package jun.learn.foundation.thread.test;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MutexEventGenerator extends IntGenerator{
	private int currentEventValue = 0;
	private Lock lock = new ReentrantLock();
	
	@Override
	public int next() {
		lock.lock();
		try {
			++currentEventValue;
			Thread.yield();
			++currentEventValue;
			return currentEventValue;
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		EventChecker.test(new MutexEventGenerator(), 10);
	}

}
