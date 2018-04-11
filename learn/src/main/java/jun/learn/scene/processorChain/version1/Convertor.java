package jun.learn.scene.processorChain.version1;

import java.util.List;

public interface Convertor{
	<T> List<T> convert(DataProvider dataProvider, Class<T> clazz);
}