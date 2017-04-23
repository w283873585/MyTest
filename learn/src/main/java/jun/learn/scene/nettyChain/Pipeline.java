package jun.learn.scene.nettyChain;

import jun.learn.scene.nettyChain.Handler.InHandler;
import jun.learn.scene.nettyChain.Handler.OutHandler;

public class Pipeline {
	private Context head;
	private Context tail; 
	{
		head = new HeadContext();
		tail = new TailContext();
		head.next = tail;
		tail.prev = head;
	}
	
	public void addLast(String name, Handler handler) {
		Context newCtx = newContext(name, handler);
		Context prev = tail.prev;
        newCtx.prev = prev;
        newCtx.next = tail;
        prev.next = newCtx;
        tail.prev = newCtx;
	}
	
	public void addFirst(String name, Handler handler) {
		Context newCtx = newContext(name, handler);
		Context nextCtx = head.next;
		newCtx.prev = head;
		newCtx.next = nextCtx;
		head.next = newCtx;
        nextCtx.prev = newCtx;
	}
	
	private Context newContext(String name, Handler handler) {
		return new Context(name, handler, this);
	}
	
	public Pipeline fireFirst() {
		head.execute();
		return this;
	}
	
	public Pipeline fireLast() {
		tail.execute();
		return this;
	}
	
	private class HeadContext extends Context implements OutHandler{
		public HeadContext() {
			super("HEAD_HANDLER", null, Pipeline.this);
		}

		@Override
		public Handler handler() {
			return this;
		}
		
		@Override
		public void read(Context ctx) {}
	}
	
	private class TailContext extends Context implements InHandler{
		public TailContext() {
			super("TAIL_HANDLER", null, Pipeline.this);
		}
		
		@Override
		public Handler handler() {
			return this;
		}

		@Override
		public void read(Context ctx) {}
	}
}
