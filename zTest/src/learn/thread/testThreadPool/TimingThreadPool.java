package com.thread.testThreadPool;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

// 线程池扩展
public class TimingThreadPool extends ThreadPoolExecutor {
	
	public TimingThreadPool(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	private final ThreadLocal<Long> startTime = new ThreadLocal<Long>();
	private final AtomicLong numTasks = new AtomicLong();
	private final AtomicLong totalTime = new AtomicLong();

	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		startTime.set(System.nanoTime());
	}
	
	protected void afterExecute(Runnable r, Throwable t) {
		try {
			long endTime = System.nanoTime();
			long taskTime = endTime - startTime.get();
			numTasks.incrementAndGet();
			totalTime.addAndGet(taskTime);
		} finally {
			super.afterExecute(r, t);
		}
	}
	
	protected void terminated() {
		try {
			System.out.println(String.format("Terminated: avg time =%dns", totalTime.get() / numTasks.get()));
		} finally {
			super.terminated();
		}
	}

}
