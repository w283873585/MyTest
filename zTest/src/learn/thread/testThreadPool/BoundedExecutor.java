package com.thread.testThreadPool;

import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.Semaphore;

// 提交指定数量任务后，自动阻塞
public class BoundedExecutor {
	private final Executor exec;
	private final Semaphore semaphore;
	
	public BoundedExecutor(Executor exec, int bound) {
		this.exec = exec;
		this.semaphore = new Semaphore(bound);
	}
	
	public void submitTask(final Runnable command) 
			throws InterruptedException {
		semaphore.acquire();
		try {
			exec.execute(new Runnable() {
				public void run() {
					try {
						command.run();
					} finally {
						semaphore.release();
					}
				}
			});
		} catch (RejectedExecutionException e) {
			semaphore.release();
		}
	}
}
