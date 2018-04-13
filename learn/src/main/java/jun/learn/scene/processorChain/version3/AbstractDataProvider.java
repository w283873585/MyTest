package jun.learn.scene.processorChain.version3;

import java.util.ArrayList;
import java.util.List;

import jun.learn.scene.processorChain.util.ReflectUtil;


/**
 * @param <S> SourceType
 */
public abstract class AbstractDataProvider<S> implements DataProvider{
	@Override
	public <T> void each(Consumer<T> c, Class<T> clazz) {
		for (S f : getList()) {
			T t = convert(f, clazz);
			t = this.<T>getConvertor().convert(f, clazz);
			c.consume(t);
		}
	}

	@Override
	public <T> List<T> getByName(String name, Class<T> c) {
		List<T> result = new ArrayList<T>();
		for (S f : getList()) {
			result.add(ReflectUtil.getValueFromObject(name, f, c));
		}
		return result;
	}

	public abstract List<S> getList();
	
	public abstract <T> T convert(S f, Class<T> t);
	
	public abstract <T> Convertor<T, S> getConvertor();
	
	public interface Convertor<T, S>{
		T convert(S s, Class<T> c);
	}
}