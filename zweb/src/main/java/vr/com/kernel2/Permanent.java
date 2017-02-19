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
	
	static <T> Permanent<T> cloneFrom(T t, Class<? extends Permanent<T>> clazz) {
		try {
			return clazz.newInstance().cloneFrom(t);
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}
}
