package vr.com.kernel2;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 可被持久化的对象,
 * 依赖于springData
 * 更简单的持久化
 */
public interface Permanent<T> {
	T toPojo();
	
	Permanent<T> cloneFrom(T t);
	
	MongoRepository<T, String> getRepository();
	
	default void persist() {
		getRepository().insert(toPojo());
	}
	
	// TODO
	@SuppressWarnings("unchecked")
	static <T, F> F cloneFrom(T t, Class<F> clazz) {
		try {
			F f = clazz.newInstance();
			((Permanent<T>) f).cloneFrom(t);
			return f;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
