package learn.atest;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class VR_AContainer<T> {
	
	/*
	 	获取指定数量不重复的数据
	 	数据源配置
	 	可以配置拦截器
	*/
	public DataContainer<T> getData(int count, InfoFilter<T> filter) {
		
		// 保证数据不重复的容器
		DataContainer<T> container = getContainer();
		
		// 用来生成资源的生成器
		Generator<T> gen = getGenerator();
		
		boolean isFull = false;
		
		while (!isFull && gen.hasNext()) {

			T current = gen.next();
			
			if (!filter.filter(current)) {
				continue;
			}
			
			container.add(current);
			isFull = count == container.size();
		}
		
		return container;
	}
	
	public abstract DataContainer<T> getContainer();
	
	public abstract Generator<T> getGenerator();
	
	
	public static class DataContainer<T>{
		private KeysUtil<T> keyUtil;
		private List<T> data = new ArrayList<T>();
		private Set<String> keySet = new HashSet<String>();
		
		public DataContainer(KeysUtil<T> keyUtil){
			this.keyUtil = keyUtil;
		}
		
		public DataContainer(){
			this(new KeysUtil<T>() {
				public String getKey(T t) {
					return null;
				}
			});
		}
		
		public void add(T t) {
			if (keySet.contains(getKey(t))) {
				return;
			}
			keySet.add(getKey(t));
			data.add(t);
		}
		
		public void addAll(List<T> list) {
			for (T t : list)
				add(t);
		}
		
		public List<T> getData() {
			return data;
		}
		
		public int size() {
			return data.size();
		}
		
		private String getKey(T t) {
			return keyUtil.getKey(t);
		}
	}
	
	public interface InfoFilter<T>{
		public abstract boolean filter(T t);
	}
	
	// 标签信息来源
	public interface Generator<T>{
		
		public T next();
		
		public boolean hasNext();
	}
	
	public interface KeysUtil<T>{
		String getKey(T t);
	}
}
