package jun.learn.scene.j2eefilter.version1;

public class Builder {
	public Processor build(String expression) {
		return new DebuggingFilter(new AuthenticationFilter(new CoreProcessor()));
	}
}
