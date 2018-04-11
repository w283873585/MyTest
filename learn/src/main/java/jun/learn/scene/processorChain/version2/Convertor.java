package jun.learn.scene.processorChain.version2;

import java.util.List;

public interface Convertor{
	<T> List<T> convert(DataProvider dataProvider, Class<T> clazz);
}