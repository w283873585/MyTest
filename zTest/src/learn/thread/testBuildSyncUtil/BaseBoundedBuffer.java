package learn.thread.testBuildSyncUtil;

import learn.thread.util.GuardedBy;
import learn.thread.util.ThreadSafe;

@ThreadSafe
public class BaseBoundBuffer<V> {
	@GuardedBy("this") private final V[] buf;
	@GuardedBy("this") private int tail;
	@GuardedBy("this") private int head;
	@GuardedBy("this") private int count;
	
	protected BaseBoundBuffer(int capacity) {
		this.buf = (V[]) new Object[capacity];
	}
	
	protected synchronized final void doPut(V v) {
		buf[tail] = v;
		if (++tail == buf.length) {
			tail = 0;
		}
		++count;
	}
	
	protected synchronized final V doTake() {
		V v = buf[head];
		buf[head] = null;
		if (++head == buf.length) {
			head = 0;
		}
		--count;
		return v;
	}
	public synchronized final boolean isFull() {
		return count == buf.length;
	}
	public synchronized final boolean isEmpty() {
		return count == 0;
	}
}