package jun.learn.foundation.thread.testModuleBuild;

import java.io.File;
import java.io.FileFilter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;


/**
 * 测试blockqueue
 * @author Administrator
 *
 */
public class TestBlockqueue implements Runnable{
	private final BlockingQueue<File> fileQueue;
	private final FileFilter fileFilter;
	private final File root;
	
	public TestBlockqueue(BlockingQueue<File> fileQueue, FileFilter fileFilter, File root) {
		this.root = root;
		this.fileFilter = fileFilter;
		this.fileQueue = fileQueue;
	}
	
	@Override
	public void run() {
		try {
			crawl(root);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
	
	private void crawl(File root) throws InterruptedException {
		File[] entries = root.listFiles(fileFilter);
		if (entries != null) {
			for (File entry : entries) {
				if (entry.isDirectory()) {
					crawl(entry);
				} else {
					fileQueue.put(entry);
				}
			}
		}
	}
	
	
	
	public static class Indexer implements Runnable {
		private final BlockingQueue<File> queue;
		
		public Indexer(BlockingQueue<File> queue) {
			this.queue = queue;
		}
		
		@Override
		public void run() {
			while (true) {
				try {
					indexFile(queue.take());
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
				}
			}
		}
		
		private void indexFile(File file) {
			System.out.println(file.getName());
		}
	}
	
	public static void startIndexing(File[] roots) {
		BlockingQueue<File> queue = new LinkedBlockingQueue<File>();
		FileFilter fileFilter = new FileFilter() {
			public boolean accept(File pathname) {
				return true;
			}
		};
		for (File root : roots) {
			new Thread(new TestBlockqueue(queue, fileFilter, root)).start();
		}
		for (int i = 0; i < 100; i++) {
			new Thread(new Indexer(queue)).start();
		}
	}
	
	public static void main(String[] args) throws InterruptedException {
		File file = new File("C:\\Users\\Administrator\\Desktop");
		File[] fileArr = {file};
		startIndexing(fileArr);
	}
}
