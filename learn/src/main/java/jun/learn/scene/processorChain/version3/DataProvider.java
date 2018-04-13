package jun.learn.scene.processorChain.version3;

import java.util.List;

public interface DataProvider {
	public String getName();
	
	public <T> void each(Consumer<T> c, Class<T> clazz);
	
	public <T> List<T> getByName(String name, Class<T> c);
	
	public interface Consumer<T>{
		void consume(T t);
	}
}