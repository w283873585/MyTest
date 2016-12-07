package jun.learn.foundation.generic;

import java.util.HashSet;
import java.util.Set;

public class TestGenericSafe {
	public static int numElementsInCommon(Set<?> set1, Set<?> set2) {
		int ret = 0;
		for(Object obj : set1){
			if(set2.contains(obj)){
				ret++ ;
			}
		}
		return ret;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void main(String[] args) {
		Set set = new HashSet();
		set.add("123456");
		set.add(123321);
		set.add("fsdafs");
		set.add("ewrwer");
		set.add(null);
		System.out.println(set.size());
		
		Set set2 = new HashSet();
		set2.add("123456");
		set2.add(123321);
		set2.add("fsdafs");
		set2.add("ewrwer");
		
		System.out.println(numElementsInCommon(set,set2));
		
	}
}
