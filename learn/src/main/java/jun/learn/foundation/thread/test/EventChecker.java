package jun.learn.foundation.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class EventChecker implements Runnable {

	private IntGenerator generator;
	private final int id;
	
	public EventChecker(IntGenerator g, int ident) {
		this.generator = g;
		this.id = ident;
	}
	
	@Override
	public void run() {
		while (!generator.isCanceled()) {
			int val = generator.next();
			//System.out.println("current" + id +" value is :" + val);
			if (val % 2 != 0 ) {
				System.out.println(val + " not even!");
				generator.cancel();
			}
		}
	}
	
	public static void test(IntGenerator gp, int count) {
		System.out.println("Press Control-C to exit");
		ExecutorService exec = Executors.newCachedThreadPool();
		for (int i = 0; i < count; i++) {
			exec.execute(new EventChecker(gp, count));
		}
		exec.shutdown();
	}

	public static void main(String[] args) {
		IntGenerator generator = new IntGenerator() {
			private int curVal = 0;
			public int next() {
				curVal++;
				Thread.yield(); //cause failure faster
				curVal++;
				return curVal;
			}
		};
		test(generator, 10);
	}
}
