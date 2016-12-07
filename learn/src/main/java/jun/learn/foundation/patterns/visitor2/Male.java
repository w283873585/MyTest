package jun.learn.foundation.patterns.visitor2;

public class Male implements Human{

	public Male(boolean hasDoHaHa, boolean hasJoinWork) {
		this.hasDoHaHa = hasDoHaHa;
		this.hasJoinWork = hasJoinWork;
	}
	
	private boolean hasDoHaHa = false;
	private boolean hasJoinWork = false;
	
	// 打哈哈
	public boolean doHaHa() {
		return hasDoHaHa;
	}
	// 是否参加工作
	public boolean doJoinWork() {
		return hasJoinWork;
	}
	
	@Override
	public void accpet(Visitor v) {
		v.visit(this);
	}
}
