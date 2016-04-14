package com.thread.testThreadAbort;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;


// 将停止服务时，未处理的任务通过TrackingExecutor用urlsToCrawl保存起来，
public abstract class WebCrawler_____gd {
	private volatile TrackingExecutor_____gd exec;
	private final Set<URL> urlsToCrawl = new HashSet<URL>();

	public WebCrawler_____gd(TrackingExecutor_____gd exec) {
	}
	
	public synchronized void start() {
		exec = new TrackingExecutor_____gd();
		for (URL url : urlsToCrawl) {
			submitCrawlTask(url);
		}
		urlsToCrawl.clear();
	}

	public void stop() {
		try {
			saveUncrawled(exec.shutdownNow());
			if (exec.awaitTermination(100L, TimeUnit.SECONDS)) {
				saveUncrawled(exec.getCancelledTasks());
			}
		} catch (InterruptedException e) {}
		finally {
			exec = null;
		}
	}
	
	protected abstract List<URL> processPage(URL url2);
	
	private void saveUncrawled(List<Runnable> uncrawled) {
		for (Runnable task : uncrawled) {
			urlsToCrawl.add(((CrawlTask) task).getPage());
		}
	}

	private void submitCrawlTask(URL url) {
		exec.execute(new CrawlTask(url));
	}
	
	private class CrawlTask implements Runnable{
		private final URL url;
		public CrawlTask(URL url) {
			this.url = url;
		}
		public void run() {
			for (URL link : processPage(url)) {
				if (Thread.currentThread().isInterrupted()) {
					return;
				}
				submitCrawlTask(link);
			}
		}
		public URL getPage() {
			return url;
		}
	}
}
