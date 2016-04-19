package learn.thread.testBuildSyncUtil;
// 利用轮询睡眠，来让取不到数据的线程在 睡眠和醒来的过程中等待目标值的到来。
public class SleepyBoundedBuffer<V> extends BaseBoundedBuffer<V>{

	protected SleepyBoundedBuffer(int capacity) {
		super(capacity);
	}
	
	public void put(V v) throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isFull()) {
					doPut(v);
					return;
				}
			}
			Thread.sleep(100);
		}
	}
	
	public V take() throws InterruptedException {
		while (true) {
			synchronized (this) {
				if (!isEmpty()) {
					return doTake();
				}
			}
			Thread.sleep(100);
		}
	}
}
