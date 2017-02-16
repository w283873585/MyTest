package vr.com.kernel2;

import org.springframework.data.mongodb.repository.MongoRepository;


/**
 * 可被持久化的对象,
 * 依赖于springData
 * 更简单的持久化
 */
public interface Permanent<T> {
	T toPojo();
	
	Permanent<T> parse(T t);
	
	MongoRepository<T, String> getRepository();
	
	default void persist() {
		getRepository().insert(toPojo());
	}
}
