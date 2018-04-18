package jun.learn.scene.processorChain.version3;

public interface DataProcessor<T>{
	String getName();
	
	void preProcess(DataProvider p, Context ctx);
	
	void process(T t, Context ctx);
}