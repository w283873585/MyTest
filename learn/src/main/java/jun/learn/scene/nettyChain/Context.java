package jun.learn.scene.nettyChain;

import jun.learn.scene.nettyChain.Handler.InHandler;
import jun.learn.scene.nettyChain.Handler.OutHandler;

public class Context {
	Context prev;
	Context next;
	
	private String name;
	private Handler handler;
	private Pipeline pipeline;
	
	private boolean isOut;
	private boolean isIn;
	
	public Context(String name, Handler handler, Pipeline pipeline) {
		this.name = name;
		this.handler = handler;
		this.pipeline = pipeline;
		
		isOut = handler() instanceof OutHandler;
		isIn = handler() instanceof InHandler;
	}
	
	public String name() { return name; }
	public Handler handler() { return handler; }
	public Pipeline pipeline() { return pipeline;}
	public void fireInEvent() { findInContext().execute(); }
	public void fireOutEvent() { findOutContext().execute(); }
	
	public Context findOutContext() {
		Context ctx = this;
		do {
            ctx = ctx.next;
        } while (!ctx.isOut);
        return ctx;
	}
	
	public Context findInContext() {
		Context ctx = this;
		do {
            ctx = ctx.prev;
        } while (!ctx.isIn);
        return ctx;
	}
	
	public void execute() {
		if (isIn) 
			((InHandler) handler()).read(this);
		else if (isOut) 
			((OutHandler) handler()).read(this);
	}
}