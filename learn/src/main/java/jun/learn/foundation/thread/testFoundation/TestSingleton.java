package jun.learn.foundation.thread.testFoundation;

import jun.learn.foundation.thread.util.ThreadUtil;
import jun.learn.foundation.thread.util.ThreadUtil.Task;

/**
 * 单例实现1
 * @author Administrator
 *
 */
public class TestSingleton {
	private TestSingleton(){}
	private static TestSingleton test1 = null;
	
	public static TestSingleton getInstance() {
		// 双层判断，避免获得不同对象
		if (test1 == null) {
			synchronized (TestSingleton.class) {
				if (test1 == null) {
					test1 = new TestSingleton();
				}
			}
		}
		return test1;
	}
	
	
	public static void main(String[] args) {
		ThreadUtil.exec(new Task() {
			@Override
			public void invoke(int index) {
				System.out.println(Thread.currentThread().toString() + TestSingleton.getInstance());
			}
		});
	}
	
	/*
	 public class Test2 {
		private Test2(){}
		// 类加载时，初始化作用域
		private static final Test2 test1 = new Test2();
		
		public static Test2 getInstance() {
			return test1;
		}
	 }
	 */
}
