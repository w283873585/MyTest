package learn.client.abox;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 */
public abstract class VR_DataSource2<T> {
	
	/*
	 	获取指定数量不重复的数据
	 	数据源配置
	 	可以配置拦截器
	*/
	public List<T> getData() {
		
		List<T> container = new ArrayList<T>();
		
		// 用来生成资源的生成器
		for (T t : getSources()) {
			container.add(t);
		}
		
		return container;
	}
	
	// 数据来源
	public abstract ResourceSet<T> getSources();
	
	// 信息过滤
	public interface InfoFilter<T>{
		public boolean filter(T t);
	}
}
