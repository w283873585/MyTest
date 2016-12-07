package jun.learn.foundation.patterns.vistor3;

public class Role {
	public void accept(AbsActor actor) {
		actor.act(this);
	}
}
