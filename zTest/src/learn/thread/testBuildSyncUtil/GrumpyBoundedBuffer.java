package learn.thread.testBuildSyncUtil;

// 通过抛出异常来防止前置条件未满足前的非法调用
public class GrumpyBoundedBuffer<V> extends BaseBoundBuffer<V>{

	protected GrumpyBoundedBuffer(int capacity) {
		super(capacity);
	}

	public synchronized void put(V v) throws BufferFullException {
		if (isFull()) {
			throw new BufferFullException();
		}
		doPut(v);
	}
	
	public synchronized V take() throws BufferEmptyException {
		if (isEmpty()) {
			throw new BufferEmptyException();
		}
		return doTake();
	}
	
	@SuppressWarnings("serial")
	public static class BufferEmptyException extends Exception{}
	@SuppressWarnings("serial")
	public static class BufferFullException extends Exception{}
}
