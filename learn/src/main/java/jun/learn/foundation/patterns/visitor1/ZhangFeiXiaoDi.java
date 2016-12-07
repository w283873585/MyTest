package jun.learn.foundation.patterns.visitor1;

public class ZhangFeiXiaoDi extends Traitor{
	
	public ZhangFeiXiaoDi(int age, String name) {
		super(age, name);
	}

	private Zhangfei zf = new Zhangfei();
	{
		// 张飞睡觉去了
		zf.sleep();
	}
	
	
	private class Zhangfei{
		// 张飞的状态 ：  sleep, active
		private String state = "active";
		private int count = 1000;
		
		
		
		public void sleep() {
			state = "sleep";
		}
		
		public String getState() {
			return state;
		}
		
		public int getSoliderCount() {
			return count;
		}
		
	}


	public boolean targetSleep() {
		if ("sleep".equals(zf.getState())) {
			System.out.println("内奸说：张飞在睡觉 你们快来");
			return true;
		}
		return false;
	}
	
	public int getSoliderCount() {
		System.out.println("内奸说： 张飞的兵力现在是" +  zf.getSoliderCount());
		return zf.getSoliderCount();
	}
	
	// 暴漏重要信息，这里可扩展暴露不同的信息
	@Override
	public void accpet(Visitor visitor) {
		visitor.visit(this);
	}
	
}
