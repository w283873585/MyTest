package jun.learn.scene.processorChain.version1;

import java.util.List;

public interface Listener<T>{
	public String getName();
	public void preHandle(List<T> data);
}