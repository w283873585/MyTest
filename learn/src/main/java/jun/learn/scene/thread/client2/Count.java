package jun.learn.scene.thread.client2;

import java.util.Random;

public class Count {
	private int count = 0;
	private Random rand = new Random(47);
	
	public synchronized int increment() {
		int temp = count;
		if (rand.nextBoolean()) {
			Thread.yield();
		}
		return (count = ++ temp);
	}
	
	public synchronized int value() {return count;}
}
