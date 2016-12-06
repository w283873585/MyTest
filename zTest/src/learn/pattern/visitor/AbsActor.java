package learn.pattern.visitor;

public class AbsActor {
	public void act(Role role) {
		role.accept(this);
		// System.out.println("演员可以演任何角色");
	}
	
	public void act(KungFuRole role) {
		System.out.println("演员都可以演功夫角色");
	}
	
	public static class YoungActor extends AbsActor {
		public void act(KungFuRole role) {
			System.out.println("最喜欢演功夫角色");
		}
	}
	
	public static class OldActor extends AbsActor {
		public void act(KungFuRole role) {
			System.out.println("年纪大了不能演");
		}
	}
	
	
	public static void main(String[] args) {
		AbsActor act = new OldActor();
		Role role = new KungFuRole();
		act.act(role);
		act.act(new KungFuRole());
		
		// vs
		AbsActor act1 = new AbsActor();
		Role role1 = new KungFuRole();
		act1.act(role1);
		
		new YoungActor().act(role1);
		// role1.accept(act1);
	}
}

