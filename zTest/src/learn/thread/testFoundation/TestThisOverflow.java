package learn.thread.testFoundation;

/**
 * this溢出
 * @author Administrator
 *
 */
public class TestThisOverflow {
	public TestThisOverflow() {
		// 内部类溢出
		new Thread() {
			public void run() {
				// this 溢出到其他线程
				// 在对象未完成构造时溢出很危险
				System.out.println(TestThisOverflow.this);
				System.out.println(this);
			}
		}.start();
		
		// 在构造函数中调用可改写的实例方法时，也会导致this引用溢出
		doSomething();
		
	}
	
	
	public void doSomething() {}
	public static void main(String[] args) {
		new TestThisOverflow();
	}
	
	
	/*
	使用工厂方法来防止this引用在构造过程中溢出
	public class SafeListener {
		private final EventListener listener;
		private SafeListener() {
			listener = new EventListener() {
				public void onEvent(Event e) {
					doSomething(e);
				}
			}
		}
		public static SafeListener newInstance(EventSource source) {
			SafeListener safe = new SafeListener();
			source.registerListener(safe.listener);
			return safe;
		}
		
	}
	*/	
}