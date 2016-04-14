package learn.atest;

import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class Test1 {
	public <V> Future<V> submit(final Callable<V> callable) {
		class MyFuture implements Future<V> {
			private CountDownLatch latch = new CountDownLatch(1);
			private V val;
			public boolean cancel(boolean mayInterruptIfRunning) {
				return false;
			}
			public boolean isCancelled() {
				return false;
			}
			public boolean isDone() {
				return false;
			}
			public V get() throws InterruptedException, ExecutionException {
				latch.await();
				return val;
			}
			public void set(V val) {
				this.val = val;
				latch.countDown();
			}
			@Override
			public V get(long timeout, TimeUnit unit) throws InterruptedException,
					ExecutionException, TimeoutException {
				return null;
			}
		}
		final MyFuture future = new MyFuture();
		new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					future.set(callable.call());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
		return future;
	}
}
