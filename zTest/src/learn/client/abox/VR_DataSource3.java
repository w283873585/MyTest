package learn.client.abox;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * 
 * 
 */
public abstract class VR_DataSource3<T> {
	
	/*
	 	获取指定数量不重复的数据
	 	数据源配置
	 	可以配置拦截器
	*/
	public List<T> getFixedData(int count, InfoFilter<T> filter) {
		
		List<T> container = new ArrayList<T>();
		Set<String> sets = new HashSet<String>();
		
		external:
		for (T cur : getDataSource()) {
			if (sets.add(getDiff().getId(cur))) {
				continue;
			}
			
			for (T saved : container) {
				if (getDiff().diff(cur, saved))
					continue external;
			}
			
			if (filter == null || filter.filter(cur)) {
				container.add(cur);
			}
			
			if (count == container.size()) {
				break;
			}
		}
		
		return container;
	}
	
	public abstract Diff<T> getDiff();
	
	// 数据来源
	public abstract Iterable<T> getDataSource();
	
	// 信息过滤
	public interface InfoFilter<T>{
		public boolean filter(T t);
	}
	
	public interface Diff<T> {
		public String getId(T t);
		
		public boolean diff(T t, T t1);
	}
	
	public static void main(String[] args) {
		Set<String> sets = new HashSet<String>();
		sets.add("1");
		System.out.println(sets.add("1"));
	}
}
