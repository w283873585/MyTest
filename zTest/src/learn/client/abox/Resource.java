package learn.client.abox;

/**
 * 
 * 
 */
public interface Resource<T> {
	public T pop();
	public boolean hasNext();
}
