package jun.learn.foundation.generic.newTest;

import java.util.ArrayList;
import java.util.List;

public class NewTest {
	
	public static class Fruit{}
	
	public static class Apple extends Fruit{}
	
	public static void main(String[] args) {
		List<Apple> fList = new ArrayList<Apple>();
		Apple apple = new Apple();
		fList.add(apple);
		List<? super Apple> f_List = fList;
		f_List.remove(apple);
		/**
		 * 
		 * List<?> 
		 * 	表示某种特定类型的非原生List, (只能放入一种具体类型, 可以是但不一定是List<Object>) 
		 * 	只是我们不知道那种类型是什么, 只能用于"引用传递"
		 * 
		 * 
		 * ? extends通配符的静态类型, 不能添加任何类型!
		 * 原因:
		 * 		List<? extends Fruit> fList2 = new ArrayList<Apple>();
		 * 		参数识别是根据静态类型来看的, 只有在完全匹配的情况下才能成功, (子类会被看做(or自动转型?)父类从而匹配成功)
		 * 		?在这表示不确定类型, (在这里继承于Fruit, 但是不代表就是Fruit)
		 * 		除了null, 没人能和?匹配; 所以不能添加各种类型  
		 * 		fList2.add(null) // 成功
		 * 
		 * 
		 * ? super通配符
		 * 		List<? super Fruit> fList2 = new ArrayList<Fruit>();
		 * 		fList2.add(new Fruit()); 	// 成功
		 * 		子类会被看做(or自动转型?)父类从而匹配成功
		 * 		
		 * 		Fruit f = fList2.get(); 	// compile error, 需要转型
		 * 
		 * 
		 * ? 作用:
		 *  	List<Fruit> ffList1 = new ArrayList<Apple>();			// compile error
		 *  	List<? extends Fruit> fList2 = new ArrayList<Apple>();	// 成功
		 *  	List<? extends Fruit> fList3 = new ArrayList<Fruit>();	// 成功
		 *  	
		 *  	List<Fruit>	fList4 = new ArrayList<Fruit>();
		 *  	List<? extends Fruit> fList5 = fList4;
		 *  可用于 "引用传递", 表示某种继承自Fruit的类型.
		 */
		/*
		fList.add(new Object());	// compile error
		fList.add(new Apple());		// compile error
		fList.add(new Fruit());		// compile error
		 */
		fList.add(null);
		
		
		/**
		 * 泛型没有协变能力
		 */
		// List<Fruit> ffList = new ArrayList<Apple>();		// compile error
	}
}
