package jun.learn.foundation.thread.testThreadAbort;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import jun.learn.foundation.thread.util.ThreadUtil;
import jun.learn.foundation.thread.util.ThreadUtil.Task;




/**
 * 线程中断
 * @author Administrator
 *
 */
public class ListenRunnable{
	private BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private Thread thread = new Thread(new Runnable() {
		@Override
		public void run() {
			try {
				while (!Thread.currentThread().isInterrupted()) {
					exec(queue.take());
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		private void exec(String str) {
			System.out.println("开始处理" + str);
		}
	});
	public void add(String str) throws InterruptedException {
		if (thread.isInterrupted() || !thread.isAlive()) {
			throw new InterruptedException();
		}
		queue.add(str);
	}
	public void start() {
		thread.start();
	}
	public void close() {
		thread.interrupt();
	}
	
	
	public static void main(String[] args) {
		final ListenRunnable test = new ListenRunnable();
		test.start();
		ThreadUtil.exec(new Task() {
			@Override
			public void invoke(int threadIndex) {
				boolean isOver = false;
				while (!isOver) {
					try {
						test.add("天下之大");
					} catch (InterruptedException e) {
						System.out.println("服务器都关了，还加个毛");
						isOver = true;
					}
				}
			}
		});
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		test.close();
	}
	
	private static final ScheduledExecutorService cancelExec = Executors.newScheduledThreadPool(10);
	public static void timedRun(final Runnable r,
								long timeout, TimeUnit unit) throws InterruptedException {
		
		// 执行完记录所产生的异常
		class RethrowableTask implements Runnable{
			private volatile Throwable t;
			
			@Override
			public void run() {
				try {
					r.run();
				} catch (Throwable t) {
					this.t = t;
				}
			}
			void rethrow(){
				if (t != null) {
					throw new RuntimeException(t);
				}
			}
		}
		
		RethrowableTask task = new RethrowableTask();
		final Thread taskThread = new Thread(task);
		taskThread.start();
		cancelExec.schedule(new Runnable() {
			public void run() { taskThread.interrupt(); }
		}, timeout, unit);
		taskThread.join(unit.toMillis(timeout));
		task.rethrow();
	}
}
