package jun.learn.foundation.patterns.vistor3;

public class KungFuRole extends Role{
	public void accept(AbsActor actor) {
		actor.act(this);
	}
}
