package jun.learn.foundation.java8;

import java.util.Arrays;
import java.util.List;

public class Lambda {
	public static void main(String[] args) {
		/**
		 * 	单行lambda表达式可以省略return
		 * 	
		 * 	@FunctionalInterface的作用
		 */
		List<String> list = Arrays.asList("c", "a", "b");
		list.sort((e1, e2) -> e1.compareTo(e2));
		/**		
		 * 		||
		 *  	||
		 */
		List<String> list2 = Arrays.asList("c", "a", "b");
		list2.sort((e1, e2) -> {
			return e1.compareTo(e2);
		});
	}
}
