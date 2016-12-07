package jun.learn.scene.thread.client1;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CriticalSection {
	static void 
	testApproaches(PairManager pman1, PairManager pman2) {
		ExecutorService exec = Executors.newCachedThreadPool();
		PairManipulator 
			pm1 = new PairManipulator(pman1),
			pm2 = new PairManipulator(pman2);
		
		PairChecker 
			pcheck1 = new PairChecker(pman1),
			pcheck2 = new PairChecker(pman2);
		
		exec.execute(pm1);
		exec.execute(pm2);
		exec.execute(pcheck1);
		exec.execute(pcheck2);
		
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch (InterruptedException e) {
			System.out.println("Sleep interrupted");
		}
		System.out.println("pm1: " + pm1 + "\npm2: " + pm2);
	}
	
	public static void main(String[] args) {
		PairManager pm1 = new PairManager1(),
				pm2 = new PairManager1();
		testApproaches(pm1, pm2);
	}
}
