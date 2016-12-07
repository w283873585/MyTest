package jun.learn.foundation.thread.testThreadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
/**
 *	一个支持取消，完成通知，以及进度通知的后台任务类
 *  内部类实现逻辑，外部类暴漏部分接口，
 *  内部类继承扩展，辅助外部类具体实现
 */
public abstract class BackgroudTask_____gd<V> implements Runnable, Future<V>{
	private final FutureTask<V> computation = new Computation();
	private ExecutorService exec = Executors.newCachedThreadPool();
	
	private class Computation extends FutureTask<V>{
		public Computation() {
			super(new Callable<V>(){
				public V call() throws Exception {
					// 执行外部类定制的comppute方法
					return BackgroudTask_____gd.this.compute();
				}
			});
		}
		// 任务完成后异步执行外部定制的onCompletion
		protected final void done() {
			exec.execute(new Runnable() {
				public void run() {
					V value = null;
					Throwable thrown = null;
					boolean cancelled = false;
					try {
						value = get();
					} catch (ExecutionException e) {
						thrown = e.getCause();
					} catch (CancellationException e) {
						cancelled = true;
					} catch (InterruptedException consumed) {
					} finally {
						onCompletion(value, thrown, cancelled);
					}
				}
			});
		}
	}
	// 任务注册在这个方法
	protected abstract V compute();
	// 任务完成通知注册在这个方法
	protected void onCompletion (V result, Throwable exception, boolean isCancelled) {}
	protected void onProgress(int current, int max) {}
	protected void setProgress(final int current, final int max) {
		exec.execute(new Runnable() {
			public void run() {
				onProgress(current, max);
			}
		});
	}
	// 任务关闭
	public boolean cancel(boolean mayInterruptIfRunning) {
		return computation.cancel(mayInterruptIfRunning);
	}
	public boolean isCancelled() {
		return computation.isCancelled();
	}
	public boolean isDone() {
		return computation.isDone();
	}
	public V get() throws InterruptedException, ExecutionException {
		return computation.get();
	}
	public V get(long timeout, TimeUnit unit) throws InterruptedException,
			ExecutionException, TimeoutException {
		return computation.get(timeout, unit);
	}
	public void run() {
		computation.run();
	}
}