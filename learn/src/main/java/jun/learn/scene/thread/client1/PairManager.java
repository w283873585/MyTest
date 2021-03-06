package jun.learn.scene.thread.client1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

abstract class PairManager {
	AtomicInteger checkCounter = new AtomicInteger(0);
	protected Pair p = new Pair();
	private List<Pair> storage =
			Collections.synchronizedList(new ArrayList<Pair>());
	public synchronized Pair getPair() {
		// Make a copy to keep the original safe:
		return new Pair(p.getX(), p.getY());
	}
	// Assume this is a time consuming operation
	protected void store(Pair p) {
		storage.add(p);
		try {
			TimeUnit.MILLISECONDS.sleep(50);
		} catch(InterruptedException ignore) {}
	}
	public abstract void increment();
}