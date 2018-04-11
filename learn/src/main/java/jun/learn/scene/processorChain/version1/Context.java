package jun.learn.scene.processorChain.version1;
public interface Context<T> {
	void invokeNext(T t);
	Object put(String key, Object value);
	Object get(Object key);
}