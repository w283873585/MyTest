package jun.learn.foundation.thread.util;

public class ThreadUtil {
	public static void exec(final Task task, int threadCount){
		for (int i = 0; i < threadCount; i++) {
			final int j = i;
			new Thread() {
				public String toString() {
					return "thread" + j;
				}
				public void run() {
					try {
						Thread.sleep(10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					task.invoke(j);
				}
			}.start();
		}
	}
	
	public static void execInOrder(final Task task, final int threadCount){
		for (int i = 0; i < threadCount; i++) {
			final int j = i;
			new Thread() {
				public String toString() {
					return "thread---" + j;
				}
				public void run() {
					try {
						Thread.sleep(10 + j * 10);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					task.invoke(j);
				}
			}.start();
		}
	}
	
	public static void exec(final Task task){
		exec(task, 10);
	}
	
	public static void start(final Task task) {
		exec(task, 1);
	}
	
	public interface Task{
		void invoke(int threadIndex);
	}
}
