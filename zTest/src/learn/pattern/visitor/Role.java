package learn.pattern.visitor;

public class Role {
	public void accept(AbsActor actor) {
		actor.act(this);
	}
}
