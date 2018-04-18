package jun.learn.scene.processorChain.version3;

public interface Context {
	void put(String key, Object value);
	
	Object get(String key);
	<T> T get(String key, Class<T> t);
}
