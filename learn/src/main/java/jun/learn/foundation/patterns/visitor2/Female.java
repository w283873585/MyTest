package jun.learn.foundation.patterns.visitor2;
public class Female implements Human{

	
	public Female(boolean hasCook, boolean hasWash) {
		this.hasCook = hasCook;
		this.hasWash = hasWash;
	}
	
	private boolean hasCook = false;
	private boolean hasWash = false;
	
	// 打哈哈
	public boolean hasCook() {
		return hasCook;
	}
	
	public boolean hasWash() {
		return hasWash;
	}
	
	@Override
	public void accpet(Visitor v) {
		v.visit(this);
	}
}