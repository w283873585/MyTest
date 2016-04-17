package learn.thread.testThreadAbort;

import java.io.File;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class PoisonPill_____gd {
	public static class IndexingService {
		private static final File POISON = new File("");
		private final IndexerThread consumer = new IndexerThread();
		private final CrawlThread producer = new CrawlThread();
		private final BlockingQueue<File> queue;
		private final File root;
		
		public IndexingService(BlockingQueue<File> queue, File root) {
			this.queue = queue;
			this.root = root;
		}
		
		public void start() {
			consumer.start();
			producer.start();
		}
		
		public void stop() {
			producer.interrupt();
		}
		public void awaitTermination() throws InterruptedException {
			consumer.join();
		}
		
		private class IndexerThread extends Thread{
			public void run() {
				try {
					while (true) {
						File file = queue.take();
						if (file == POISON) {
							break;
						} else {
							indexFile(file);
						}
					}
				} catch (InterruptedException e) {}
			}
			private void indexFile(File file) {}
		}
		
		
		private class CrawlThread extends Thread{
			public void run() {
				try {
					crawl(root);
				} catch (InterruptedException e) {}
				finally {
					while (true) {
						try {
							queue.put(POISON);
							break;
						} catch (InterruptedException e) {}
					}
				}
			}

			private void crawl(File root) throws InterruptedException{
				
			}
		}
	}
	
	// 多线程检查多站点是否有新邮件，
	boolean checkEmail(Set<String> hosts, long timeout, TimeUnit unit) 
			throws InterruptedException {
		ExecutorService exec = Executors.newCachedThreadPool();
		final AtomicBoolean hasNewEmail = new AtomicBoolean(false);
		try {
			for (final String host : hosts) {
				exec.execute(new Runnable() {
					public void run() {
						if (checkEmail(host)) {
							hasNewEmail.set(true);
						}
					}
					private boolean checkEmail(String host) {
						return false;
					}
				});
			}
		} finally {
			exec.shutdown();
			exec.awaitTermination(timeout, unit);
		}	
		return hasNewEmail.get();
	}
}
