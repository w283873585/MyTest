package jun.learn.foundation.patterns.visitor1;

public class SunQuan implements Visitor{

	@Override
	public void visit(Traitor ac) {
		if (ac.getSoliderCount() < 2000) {
			this.dispatch();
		}
	}
	
	void dispatch() {
		System.out.println("哈哈，我孙权要出兵打张飞了");
	}
}
