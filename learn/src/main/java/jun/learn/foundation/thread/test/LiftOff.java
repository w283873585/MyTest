package jun.learn.foundation.thread.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class LiftOff implements Runnable{
	protected int countDown = 10;
	private static int taskCount = 0;
	private final int id = taskCount++;
	public LiftOff() {}
	public LiftOff(int countDown) {
		this.countDown = countDown;
	}
	
	public String status() {
		return "#" + id + "(" +
				(countDown > 0 ? countDown : "Liftoff!") + ").";
	}
	
	public void run() {
		while (countDown-- > 0) {
			System.out.println(status());
			Thread.yield();
		}
	}
	
	public static void main(String[] args) {
		ExecutorService es = Executors.newCachedThreadPool();
		es.execute(new LiftOff());
		es.shutdown();
		
	}
}
