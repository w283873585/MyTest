package jun.learn.foundation.patterns.visitor1;

public class SimpleVisitor implements Visitor{

	@Override
	public void visit(Traitor ac) {
		System.out.println("我已经访问到他的姓名:" + ac.getName());
		System.out.println("我已经访问到他的年龄:" + ac.getAge());
	}
	
	public static void main(String[] args) {
		Traitor t = new SimpleTraitor(18, "西施");
		Visitor v = new SimpleVisitor();
		t.accpet(v);
	}
}
