package learn.pattern.visitor;

public class KungFuRole extends Role{
	public void accept(AbsActor actor) {
		actor.act(this);
	}
}
