package jun.learn.foundation.java8;

public class ThreadUtil {
	private static int index = 0;
	
	public static void exec(Runnable runable) {
		Thread thread = new Thread(runable);
		thread.setName("线程" + (++index) + "号");
		thread.start();
	}
}
