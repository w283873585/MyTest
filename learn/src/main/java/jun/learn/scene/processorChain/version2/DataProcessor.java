package jun.learn.scene.processorChain.version2;

import java.util.List;

public interface DataProcessor<T>{
	String getName();
	
	void beforeProcess(List<T> data);
	
	void process(T t);
}