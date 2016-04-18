package learn.thread.testBuildSyncUtil;

// 条件队列
// 使用对象的wait和notify实现线程通信
// 来阻塞非法操作
public class BoundedBuffer<V> extends BaseBoundBuffer<V> {

	protected BoundedBuffer(int capacity) {
		super(capacity);
	}
	public synchronized void put(V v) throws InterruptedException{
		// 为什么是while
		// 醒来后需要继续判断
		// 否则在醒来的同时，缓存可能已经被放满了
		while (isFull()) {
			wait();
		}
		doPut(v);
		notifyAll();
	}
	public synchronized V take() throws InterruptedException {
		while (isEmpty()) {
			wait();
		}
		V v = doTake();
		notifyAll();
		return v;
	}
}