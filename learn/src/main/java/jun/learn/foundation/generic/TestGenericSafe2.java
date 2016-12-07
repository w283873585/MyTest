package jun.learn.foundation.generic;

import java.util.ArrayList;
import java.util.List;

public class TestGenericSafe2 {
	/**
	static Object reduce (@SuppressWarnings("rawtypes") List list, Function f, Object initVal) {
		synchronized (list) {
			Object result = initVal;
			for (Object o : list) {
				result = f.apply(result, o);
			}
			return result;
		}
	}
	*/
	/**
	static <E> E reduce(List<E> list, Function<E> f, E initVal) {
		E[] snapshot = (E[])list.toArray();
		E result = initVal;
		for (E e : snapshot) {
			result  = f.apply(result, e);
		}
		return result;
	}
	*/
	
	static <E> E reduce(List<E> list, Function<E> f, E initVal) {
		List<E> snapshot;
		synchronized (list) {
			snapshot = new ArrayList<E>(list);
		}
		E result = initVal;
		for (E e : snapshot) {
			result  = f.apply(result, e);
		}
		return result;
	}
}
