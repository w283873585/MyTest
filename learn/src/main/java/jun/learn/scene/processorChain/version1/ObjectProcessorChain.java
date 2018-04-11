package jun.learn.scene.processorChain.version1;
public interface ObjectProcessorChain<T>{
	void process(T t);
	void addProcessor(DataProcessor<T> processor);
}