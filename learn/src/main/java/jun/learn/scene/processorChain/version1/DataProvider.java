package jun.learn.scene.processorChain.version1;

import java.util.Map;

public interface DataProvider{
	boolean hasNext();
	Map<String, Object> next();
}