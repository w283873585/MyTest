package vr.com.data;

import java.util.List;

public interface Result {
	public <T> T toBean(Class<T> c);
	
	public <T> List<T> toList(Class<T> c);
	
	public boolean isSuccess();
}
