package learn.atest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ClientForTestThread {
	
	public static abstract class IntGenerator {
		private volatile boolean canceled = false;
		public abstract int next();
		public void cancel() { canceled = true; }
		public boolean isCanceled() { return canceled; }
	}
	
	public static class EventChecker implements Runnable{
		private IntGenerator gen;
		private final int id;
		
		public EventChecker(IntGenerator gen, int id) {
			this.gen = gen;
			this.id = id;
		}
		
		@Override
		public void run() {
			while (!gen.isCanceled()) {
				int val = gen.next();
				if (val % 2 != 0) {
					System.out.println(val + " not even");
					gen.cancel();
				}
			}
		}
		
		public static void test(IntGenerator gen, int count) {
			System.out.println("Press Control-C to exit");
			ExecutorService exec = Executors.newCachedThreadPool();
			for (int i = 0; i < count; i++) {
				exec.execute(new EventChecker(gen, i));
			}
			exec.shutdown();
		}
		
		public static void test(IntGenerator gen) {
			test(gen, 10);
		}
	}
	
	public static class EvenGenerator extends IntGenerator {
		
		private int currentEvenValue = 0;
		
		@Override
		public int next() {
			synchronized (this) {
				++currentEvenValue;
				++currentEvenValue;
				return currentEvenValue;
			}
		}
	}
	
	
	public static void main(String[] args) {
		EventChecker.test(new EvenGenerator());
	}
}
