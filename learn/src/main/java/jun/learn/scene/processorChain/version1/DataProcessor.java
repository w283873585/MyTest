package jun.learn.scene.processorChain.version1;
public interface DataProcessor<T> {
	public String getName();
	public void process(T t, Context<T> ctx);
}