package jun.learn.foundation.patterns.dutyChain_spring;

public interface Interceptor {
	public Object invoke(Invocation invocation);
}
