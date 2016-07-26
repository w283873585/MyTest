package learn.client.abox;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * 
 * 
 */
public abstract class ResourceSet<T> implements Iterable<T>{
	private List<Resource<T>> list = new ArrayList<Resource<T>>();
	
	public void add(Resource<T> r) {
		list.add(r);
	}
	
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			private int index = 0;
			private Resource<T> curResource = list.get(index++);
			private T current = _next();
			
			public boolean hasNext() {
				return current != null;
			}

			public T next() {
				if (!hasNext()) {
					throw new RuntimeException("no data anymore");
				}
				T result = current;
				current = _next();
				return result;
			}
			
			private T _next() {
				if (curResource.hasNext()) {
					return curResource.pop();
				}
				if (index < list.size()) {
					curResource = list.get(index++);
					return _next();
				}
				return null;
			}

			public void remove() {
				throw new RuntimeException("no support remove");
			}
		};
	}
}
