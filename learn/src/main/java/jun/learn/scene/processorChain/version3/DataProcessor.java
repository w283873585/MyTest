package jun.learn.scene.processorChain.version3;

public interface DataProcessor<T>{
	String getName();
	
	void beforeProcess(DataProvider p);
	
	void process(T t);
}