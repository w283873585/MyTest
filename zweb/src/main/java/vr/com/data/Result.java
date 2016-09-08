package vr.com.data;

import java.util.List;

public interface Result {
	public <T> T getBean(Class<T> c);
	
	public <T> List<T> getList(Class<T> c);
	
	public boolean isSuccess();
}
