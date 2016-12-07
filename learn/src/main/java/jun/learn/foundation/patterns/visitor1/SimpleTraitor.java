package jun.learn.foundation.patterns.visitor1;

public class SimpleTraitor extends Traitor{

	public SimpleTraitor(int age, String name) {
		super(age, name);
	}

	// 内奸方法,放访问者进入，
	// 然后调用访问者的方法，传递一些必要信息
	@Override
	public void accpet(Visitor visitor) {
		visitor.visit(this);
	}

	@Override
	public boolean targetSleep() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getSoliderCount() {
		// TODO Auto-generated method stub
		return 0;
	}
}
