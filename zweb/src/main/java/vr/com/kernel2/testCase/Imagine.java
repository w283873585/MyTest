package vr.com.kernel2.testCase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;

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

	public Context invoke() {
		Context ctx = new SimpleContext();
		
		// parse the expression list ordinally
		for (String exp : expressions) {
			try {
				getResolver().resolve(exp, ctx);
			} catch (RuntimeException e) {
			    ctx.commit(e.getMessage());
			} catch (Exception e) {
				ctx.commit("服务器发生了未期望的异常：" + e.getMessage());
			}
			
			// break immediate if context has be committed
			if (ctx.hasCommitted()) 
				break;
		}
		
		// submit success finally
		if (!ctx.hasCommitted()) 
			ctx.commit("test passed!");
		
		return ctx;
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
	 * 
	 * 扩展: 记录每一步的执行结果与参数
	 */
	public static class SimpleContext implements Context{

		private String msg = null;
		private boolean committed = false;
		
		/**
		 * 上下文的数据池， 每次请求产生的结果数据都会存在这
		 */
		private Map<String, Object> ctxPool = new HashMap<String, Object>();
		
		/**
		 * 每次流程产生的相关数据
		 */
		private String name = null;
		private Map<String, Object> param = new HashMap<String, Object>();
		private Map<String, Object> result = new HashMap<String, Object>();
		
		/**
		 * 在finishCurrent执行后，会将每次流程产生的相关数据汇总在这
		 */
		private List<Map<String, Object>> flows = new ArrayList<Map<String, Object>>();
		
		@Override
		public Object get(String key) {
			return ctxPool.get(key);
		}

		@Override
		public void put(String key, Object value) {
			ctxPool.put(key, value);
			this.result.put(key, value);
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

        @Override
        public void setCurrentName(String name) {
            this.name = name;
        }

        @Override
        public void addCurrentParam(String key, Object value) {
           this.param.put(key, value);
        }

        @Override
        public void finishCurrent() {
            Map<String, Object> current = new HashMap<String, Object>();
            current.put("name", name);
            current.put("params", param);
            current.put("result", result);
            flows.add(current);
            
            // clear
            name = null;
            param = new HashMap<String, Object>();
            result = new HashMap<String, Object>();
        }

        @Override
        public String getAll() {
            return JSON.toJSONString(flows);
        }
	}
}
