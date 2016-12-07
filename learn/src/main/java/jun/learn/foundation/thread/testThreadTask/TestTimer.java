package jun.learn.foundation.thread.testThreadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;

import javax.sound.sampled.Port.Info;





/**
 */
public class TestTimer{
	public static void main(String[] args) throws InterruptedException {
		Timer timer = new Timer();
		timer.schedule(new ThrowTask(), 1);
		Thread.sleep(1);
		timer.schedule(new ThrowTask(), 1);
		Thread.sleep(5);
	}
	
	static class ThrowTask extends TimerTask {

		@Override
		public void run() {
			throw new RuntimeException();
		}
	}
	
	// 异步渲染文字和渲染图片
	public static class FuntureRenderer {
		private final ExecutorService exec = Executors.newFixedThreadPool(10);
		
		void renderPage(CharSequence source) {
			final List<ImageInfo> imageInfos = scanForImageInfo(source);
			Callable<List<ImageData>> task = 
					new Callable<List<ImageData>>() {
						public List<ImageData> call() throws Exception {
							List<ImageData> result = new ArrayList<ImageData>();
							for (ImageInfo imageInfo : imageInfos) {
								result.add(imageInfo.downloadImage());
							}
							return result;
						}
					};
			Future<List<ImageData>> future = exec.submit(task);
			renderText(source);
			try {
				List<ImageData> imageData = future.get();
				for (ImageData data : imageData) {
					renderImage(data);
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
				future.cancel(true);
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		private void renderImage(ImageData data) {
			// TODO Auto-generated method stub
		}

		private void renderText(CharSequence source) {
		}

		private List<ImageInfo> scanForImageInfo(CharSequence source) {
			return null;
		}

		public static class ImageInfo{
			public ImageData downloadImage() {
				return null;
			}
		}
		public static class ImageData{}
	}
	
	/**
	 * 依次渲染图片
	 * @author Administrator
	 *
	 */
	public static class FuntureRenderer1 {
		private final ExecutorService exec = Executors.newFixedThreadPool(10);
		private final BlockingQueue<ImageData> queue = new LinkedBlockingQueue<ImageData>();
		
		void renderPage(CharSequence source) {
			final List<ImageInfo> imageInfos = scanForImageInfo(source);
			for (ImageInfo info : imageInfos) {
				exec.execute(getTask(info));
			}
			renderText(source);
			try {
				ImageData imageData = queue.take();
				renderImage(imageData);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		
		private Runnable getTask(final ImageInfo imageInfo) {
			return new Runnable() {
				public void run() {
					try {
						queue.put(imageInfo.downloadImage());
					} catch (InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			};
		}
		
		private void renderImage(ImageData data) {}

		private void renderText(CharSequence source) {}

		private List<ImageInfo> scanForImageInfo(CharSequence source) {
			return null;
		}

		public static class ImageInfo{
			public ImageData downloadImage() {
				return null;
			}
		}
		public static class ImageData{}
	}
	
	/**
	 * 依次渲染图片
	 * @author Administrator
	 *
	 */
	public static class FuntureRenderer2 {
		private final ExecutorService exec = Executors.newFixedThreadPool(10);
		
		void renderPage(CharSequence source) {
			final List<ImageInfo> imageInfos = scanForImageInfo(source);
			CompletionService<ImageData> completionService = new ExecutorCompletionService<>(exec);
			for (ImageInfo info : imageInfos) {
				completionService.submit(getTask(info));
			}
			renderText(source);
			try {
				for (int i = 0, n = imageInfos.size(); i < n; i++) {
					Future<ImageData> imageData = completionService.take();
					renderImage(imageData.get());
				}
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			} catch (ExecutionException e) {
				e.printStackTrace();
			}
		}
		
		private Callable<ImageData> getTask(final ImageInfo imageInfo) {
			return new Callable<ImageData>() {
				public ImageData call() throws Exception {
					return imageInfo.downloadImage();
				}
			};
		}
		
		private void renderImage(ImageData data) {}

		private void renderText(CharSequence source) {}

		private List<ImageInfo> scanForImageInfo(CharSequence source) {
			return null;
		}

		public static class ImageInfo{
			public ImageData downloadImage() {
				return null;
			}
		}
		public static class ImageData{}
	}
	
}
