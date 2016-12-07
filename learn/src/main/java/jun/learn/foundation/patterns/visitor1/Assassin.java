package jun.learn.foundation.patterns.visitor1;

public class Assassin implements Visitor{

	// 负责访问 张飞睡眠情况
	@Override
	public void visit(Traitor ac) {
		if(ac.targetSleep()) {
			this.attack();
		}
	}

	public void attack() {
		System.out.println("我要去暗杀张飞了");
	}
}
