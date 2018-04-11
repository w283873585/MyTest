package jun.learn.scene.processorChain.version2;

import java.util.Map;

public interface DataProvider{
	boolean hasNext();
	Map<String, Object> next();
}