package vr.com.kernel2.chain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Imagine {
	/**
	 * expression:
	 * 		interface {id},2,3,4 expect
	 */
	private Resolver resolver;
	private List<String> expressions = new ArrayList<String>();
	
	public Imagine(Resolver resolver) {
		this.resolver = resolver;
	}
	
	public void addNode(String expression) {
		expressions.add(expression);
	}

	public void invoke() {
		Context ctx = new SimpleContext();
		// parse the expression list ordinally
		for (String exp : expressions) {
			try {
				getResolver().resolve(exp, ctx);
			} catch (Exception e) {
				ctx.commit(e.getMessage());
			}
			
			// break immediate if context has be committed
			if (ctx.hasCommitted()) 
				break;
		}
		
		// submit success finally
		if (!ctx.hasCommitted()) 
			ctx.commit("test passed!");
	}
	
	public Resolver getResolver() {
		/**
		 * 
		 * String interface = getInterface(expression);
		 * String parameters = getParam(expression);
		 * String expect = getExpect(expression);
		 * 
		 * result = doRequest(interface, process(parameters))
		 * if (!process(expect).match(result))
		 * 		ctx.setCommited("result no matched")
		 * 
		 */
		return this.resolver;
	}
	
	
	/**
	 * 简单的表达式上下文实现
	 */
	public static class SimpleContext implements Context{

		private String msg = null;
		private boolean committed = false;
		private Map<String, Object> map = new HashMap<String, Object>();
		
		@Override
		public Object get(String key) {
			return map.get(key);
		}

		@Override
		public void put(String key, Object value) {
			map.put(key, value);
		}

		@Override
		public boolean hasCommitted() {
			return committed;
		}

		@Override
		public void commit(String msg) {
			this.msg = msg;
			this.committed = true;
		}

		@Override
		public String getMsg() {
			return this.msg;
		}
	}
}
