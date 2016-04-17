package learn.thread.testThreadAbort;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;
import java.util.concurrent.RunnableFuture;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;





/**
 * 批量执行任务
 * @author Administrator
 *
 */
public class CancellableTaskConstructor_____gd{
	/**
	 * 	ThreadPoolExecutor提供了一个工厂方法newTaskFor，用来返回将runnable类型加工成runnableFuture
	 *  这里首先抽象一个CancelableTask， 可以定制cancel和newTask的接口。
	 *  然后定义一个特别的Executor，他的区别是他的工厂方法给刚刚抽象出来的CancelableTask提供一个生产RunnableFuture的其他途径。通过调用newTask
	 *  最后定义一个socket专用的，实现了接口cancelableTask的抽象类，他定制了cancel和newTask方法。可以用来生产具体的RunnableFuture。
	 * 
	 *
	 */
	public interface CancellableTask<T> extends Callable<T> {
		void cancel();
		RunnableFuture<T> newTask();
	}
	
	public static class CancellingExecutor extends ThreadPoolExecutor {
		public CancellingExecutor(int corePoolSize, int maximumPoolSize,
				long keepAliveTime, TimeUnit unit,
				BlockingQueue<Runnable> workQueue) {
			super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
		}
		
		protected<T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
			if (callable instanceof CancellableTask) {
				return ((CancellableTask<T>)callable).newTask();
			} else {
				return super.newTaskFor(callable);
			}
		}
	}
	
	public abstract class SocketUsingTask<T> implements CancellableTask<T> {
		private Socket socket;

		protected synchronized void setSocket(Socket s) { socket = s;}
		
		public synchronized void cancel() {
			try {
				if (socket != null) {
					socket.close();
				}
			} catch (IOException ignored) {}
		}
		
		public RunnableFuture<T> newTask() {
			return new FutureTask<T>(this) {
				public boolean cancel(boolean mayInterruptIfRunning) {
					try {
						SocketUsingTask.this.cancel();
					} finally {
						return super.cancel(mayInterruptIfRunning);
					}
				}
			};
		}
	}
}