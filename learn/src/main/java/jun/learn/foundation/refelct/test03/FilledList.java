package jun.learn.foundation.refelct.test03;

import java.util.ArrayList;
import java.util.List;

public class FilledList<T>{
	
	private Class<T> type;
	public FilledList(Class<T> type){
		this.type = type;
	}

	public List<T> create(int nElements){
		List<T> result = new ArrayList<T>();
		try {
			for(int i=0;i<nElements;i++)
			result.add(type.newInstance());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return result;
	}
	
	public static void main(String[] args) {
		FilledList<CountedInteger> f1 = new FilledList<CountedInteger>(CountedInteger.class);
		System.out.println(f1.create(15));
	}
}
