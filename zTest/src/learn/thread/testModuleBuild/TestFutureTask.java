package learn.thread.testModuleBuild;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;




/**
 * FutureTask
 * @author Administrator
 *
 */
public class TestFutureTask{
	private final FutureTask<Boolean> future = new FutureTask<Boolean>(new Callable<Boolean>() {
		@Override
		public Boolean call() throws Exception {
			return true;
		}
	});
	private final Thread thread = new Thread(future);
	public void start() {
		thread.start();
	}
	
	public boolean isReady() {
		try {
			return future.get();
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
		return false;
	}
}
