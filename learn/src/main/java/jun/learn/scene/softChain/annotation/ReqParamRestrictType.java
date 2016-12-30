package jun.learn.scene.softChain.annotation;

import java.util.concurrent.ConcurrentHashMap;

import com.alibaba.fastjson.JSONArray;

public enum ReqParamRestrictType {
	
	/**
	 * 参数可为空
	 */
	optional("可选") {
		@Override
		protected boolean isPass(Object c) {
			return true;
		}
	},
	
	/**
	 * 参数不能为空
	 */
	NoEmpty("参数不能为空"){
		@Override
		protected boolean isPass(Object c) {
			return c != null;
		}
	},
	
	/**
	 * 必须为rsa加密, extends NoEmpty
	 */
	Rsa("RSA解密失败", NoEmpty){
		@Override
		public Result _check(Object o) {
			Object obj =  o; // ValidateRSAUtil.decByPrivateKey(o.toString());
			if (obj != null) {
				return new Result(true, obj);
			}
			return getResult(false, info);
		}

		@Override
		protected boolean isPass(Object c) {
			return true;
		}
	}, 
	
	/**
	 * 必须为列表类型, extends NoEmpty
	 */
	list("参数必须为列表类型", NoEmpty) {
		@Override
		protected boolean isPass(Object c) {
			return c instanceof JSONArray;
		}
	},
	
	/**
	 * 必须为纯数字, extends NoEmpty
	 */
	number("必须为数字", NoEmpty){
		@Override
		protected boolean isPass(Object c) {
			return c.toString().matches("[0-9]+");
		}
	},
	
	/**
	 * 长度必须为6, extends NoEmpty
	 */
	length6("长度必须为6", NoEmpty){
		@Override
		protected boolean isPass(Object c) {
			return c.toString().length() == 6;
		}
	},
	
	/**
	 * 长度必须为16, extends NoEmpty
	 */
	length16("长度必须为16", NoEmpty){
		@Override
		protected boolean isPass(Object c) {
			return c.toString().length() == 16;
		}
	};
	
	ReqParamRestrictType(String info, ReqParamRestrictType parent) {
		this.info = info;
		this.parent = parent;
	}
	
	ReqParamRestrictType(String info) {
		this(info, null);
	}
	
	protected String info;
	
	private ReqParamRestrictType parent;
	
	public Result check(Object c) {
		if (parent != null) {
			Result result = parent.check(c);
			if (!result.isSuccess()) {
				return result;
			}
		}
		return this._check(c);
	}
	
	protected Result _check(Object c) {
		boolean success = false;
		if (this.isPass(c)) {
			success = true;
		}
		return getResult(success, info);
	}
	
	protected abstract boolean isPass(Object c);
	
	/**
	 * 
	 * 
	 **/
	private static final ConcurrentHashMap<String, Result> pool = 
			new ConcurrentHashMap<String, ReqParamRestrictType.Result>();
	
	private static Result getResult(boolean success, String info) {
		String key = success ? "success" : "failed" + info;
		Result r = pool.get(key);
		if (r == null) {
			synchronized (ReqParamRestrictType.class) {
				r = new Result(success, info);
				pool.put(key, r);
			}
		}
		return r;
	}
	
	public static class Result{
		private boolean success;
		private Object result;
		
		public Result(boolean suc, Object o) {
			this.success = suc;
			this.result = o;
		}
		
		public boolean isSuccess() {
			return success;
		}
		
		public Object getResult() {
			return result;
		}
	}
}
