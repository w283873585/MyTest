package com.thread.testThreadAbort;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.thread.util.ThreadUtil;
import com.thread.util.ThreadUtil.Task;

public class LogService_____gd {
	private final BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private final LoggerThread loggerThread = new LoggerThread();
	private final PrintWriter writer;
	private boolean isShutdown = false;
	private int reservations = 0;
	
	public LogService_____gd(String path) throws FileNotFoundException, UnsupportedEncodingException {
		writer = new PrintWriter(path, "utf-8");
	}
	
	public void start() { loggerThread.start(); }
	
	public void stop() {
		synchronized (this) {
			isShutdown = true;
		}
		loggerThread.interrupt();
		System.out.println("LogService has stopped!");
	}
	
	public void log(String msg) throws InterruptedException {
		synchronized (this) {
			if (isShutdown) {
				throw new IllegalStateException("....");
			}
			++reservations;
		}
		queue.put(msg);
	}
	
	private class LoggerThread extends Thread{
		public void run() {
			try {
				while (true) {
					try {
						synchronized (LogService_____gd.this) {
							if (isShutdown && reservations == 0) {
								break;
							}
						}
						String msg = queue.take();
						synchronized (LogService_____gd.this) {
							--reservations;
						}
						writer.println(msg);
					} catch (InterruptedException e) { /* retry */}
				}
			} finally {
				writer.close();
			}
		}
	}
	
	
	public static void main(String[] args) throws FileNotFoundException, InterruptedException, UnsupportedEncodingException {
		final LogService_____gd logServer = new LogService_____gd("C:\\Users\\Administrator\\Desktop\\tian.txt");
		logServer.start();
		
		ThreadUtil.exec(new Task(){
			public void invoke(int threadIndex) {
				try {
					logServer.log("我爱北京天安门" + threadIndex);
				} catch (InterruptedException e) {}
			}
		});
		Thread.sleep(5000);
		logServer.stop();
	}
}