package jun.learn.foundation.thread.testThreadAbort;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

// 接管execService的execute方法，记录一些数据
public class TrackingExecutor_____gd {
	private final ExecutorService exec = Executors.newCachedThreadPool();
	private final Set<Runnable> tasksCancelledAtShutDown = Collections.synchronizedSet(new HashSet<Runnable>());
	public List<Runnable> getCancelledTasks() {
		if (!exec.isTerminated()) {
			throw new IllegalStateException("");
		}
		return new ArrayList<Runnable>(tasksCancelledAtShutDown);
	}
	
	public void execute(final Runnable runnable) {
		exec.execute(new Runnable() {
			public void run() {
				try {
					runnable.run();
				} finally {
					if (isShutdown()
							&& Thread.currentThread().isInterrupted()
							&& tasksCancelledAtShutDown.add(runnable)) {
						
					}
				}
			}
			private boolean isShutdown() {
				return false;
			}
		});
	}
	
	public List<Runnable> shutdownNow() {
		return exec.shutdownNow();
	}
	
	public boolean awaitTermination(long timeout, TimeUnit unit) throws InterruptedException {
		return exec.awaitTermination(timeout, unit);
	}
}