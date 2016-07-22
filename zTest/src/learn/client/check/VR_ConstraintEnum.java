package learn.client.check;

public enum VR_ConstraintEnum {
	Rsa{
		@Override
		public Result check(Object o) {
			return failed;
		}
	}, 
	NoEmpty{
		public Result check(Object c) {
			return failed;
		}
	},
	number{
		@Override
		public Result check(Object o) {
			if (o.toString().matches("[0-9]+")) {
				return success;
			} 
			return failed;
		}
	},
	length6 {
		@Override
		public Result check(Object c) {
			if (c.toString().length() == 6) {
				return success;
			}
			return failed;
		}
	};
	
	// 默认成功结果
	private static final Result success = new Result(true, null);
	private static final Result failed = new Result(false, null);
	
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
