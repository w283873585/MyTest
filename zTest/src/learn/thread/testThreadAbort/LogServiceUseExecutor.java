package com.thread.testThreadAbort;

import static java.util.concurrent.TimeUnit.MINUTES;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.RejectedExecutionException;

import com.thread.util.ThreadUtil;
import com.thread.util.ThreadUtil.Task;

public class LogServiceUseExecutor {
	private final ExecutorService exec = Executors.newSingleThreadExecutor();
	private final PrintWriter writer;
	
	public LogServiceUseExecutor(String path) throws FileNotFoundException, UnsupportedEncodingException {
		writer = new PrintWriter(path, "utf-8");
	}
	
	public void start() {}
	public void stop() throws InterruptedException {
		try {
			exec.shutdown();
			exec.awaitTermination(100, MINUTES);
		} finally {
			writer.close();
		}
	}
	
	public void log(String msg) {
		try {
			exec.execute(new WriteTask(msg));
		} catch (RejectedExecutionException ignored) {}
	}
	
	private class WriteTask implements Runnable {
		private final String msg;
		public WriteTask(String msg) {
			this.msg = msg;
		}
		public void run() {
			writer.print(msg);
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, UnsupportedEncodingException {
		final LogServiceUseExecutor logServer = new LogServiceUseExecutor("C:\\Users\\Administrator\\Desktop\\tian.txt");
		logServer.start();
		
		ThreadUtil.exec(new Task(){
			public void invoke(int threadIndex) {
				logServer.log("我爱北京天安门" + threadIndex);
			}
		});
		Thread.sleep(5000);
		logServer.stop();
	}
}