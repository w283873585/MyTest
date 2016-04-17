package learn.thread.testThreadAbort;

public class UncaughtExceptionHandler implements Thread.UncaughtExceptionHandler{

	@Override
	public void uncaughtException(Thread t, Throwable e) {
		System.out.println("Thread terminated with exception:" + t.getName());
	}
	
	
	public static void main(String[] args) {
		Thread t = new Thread();
		t.setUncaughtExceptionHandler(new UncaughtExceptionHandler());
	}
	
	
	// 典型的线程池工作者线程结构
	private static class WorkRunnable extends Thread {

		@Override
		public void run() {
			Throwable thrown = null;
			try {
				while (!isInterrupted()) {
					runTask(getTaskFromWorkQueue());
				}
			} catch (Throwable t) {
				thrown = t;
			} finally {
				threadExited(this, thrown);
			}
		}

		private void threadExited(WorkRunnable workRunnable, Throwable thrown) {
		}

		private void runTask(Object taskFromWorkQueue) {
		}

		private Object getTaskFromWorkQueue() {
			return null;
		}
		
	}
}
