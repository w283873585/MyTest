package learn.client.check;

import java.util.concurrent.ConcurrentHashMap;

public enum VR_ConstraintEnum {
	Rsa("RSA解密失败"){
		@Override
		public Result check(Object o) {
			if (o.toString().contains("rsa")) {
				return new Result(true, o.toString() + "rsa");
			}
			return getResult(false, info);
		}
	}, 
	NoEmpty("参数不能为空"){
		public Result check(Object c) {
			boolean success = false;
			if (c != null) {
				success = true;
			}
			return new Result(success, info);
		}
	},
	number("必须为数字"){
		@Override
		public Result check(Object o) {
			boolean success = false;
			if (o.toString().matches("[0-9]+")) {
				success = true;
			}
			return new Result(success, info);
		}
	},
	length6("长度必须为6"){
		@Override
		public Result check(Object c) {
			boolean success = false;
			if (c.toString().length() == 6) {
				success = true;
			}
			return new Result(success, info);
		}
	};
	
	VR_ConstraintEnum(String info) {
		this.info = info;
	}
	
	String info;
	
	private static final ConcurrentHashMap<String, Result> pool = 
			new ConcurrentHashMap<String, VR_ConstraintEnum.Result>();
	
	private static Result getResult(boolean success, String info) {
		String key = success ? "success" : "failed" + info;
		Result r = pool.get(key);
		if (r == null) {
			synchronized (VR_ConstraintEnum.class) {
				r = new Result(success, info);
				pool.put(key, r);
			}
		}
		return r;
	}
	
	public abstract Result check(Object c);
	
	public static class Result{
		public boolean success;
		public Object result;
		
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
