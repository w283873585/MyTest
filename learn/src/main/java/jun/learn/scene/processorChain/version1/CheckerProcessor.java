package jun.learn.scene.processorChain.version1;

public class CheckerProcessor<T> implements DataProcessor<T> {
	@Override
	public void process(T t, Context<T> ctx) {
		System.out.println("check");
		ctx.invokeNext(t);
	}

	@Override
	public String getName() {
		return null;
	}
}