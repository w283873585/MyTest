package jun.learn.foundation.innerClass;

public class TestForPartInnerClass {
	/**
		 局部内部类也像别的类一样进行编译，
		 但只是作用域不同而已，
		 只在该方法或条件的作用域内才能使用，
		 退出这些作用域后无法引用的。
	*/
	
	/**	局部内部类定义在方法内 **/
	public TestForPartInnerInterface get(String s){
		
		/**
		 * 局部内部类
		 * 当所在的方法的形参需要被内部类里面使用时，该形参必须为final。
		 * ----------------原因--------------
		 * 内部类和外围类在编译时会生成两个文件，
		 * 内部内在调用外围类方法形参时，
		 * 用的是引用传递，即拷贝引用，
		 * 为了避免引用值发生改变，
		 * 例如被外部类的方法修改等，而导致内部类得到的值不一致，
		 * 于是用final来让该引用不可改变)
		 * 
		 */
		
		class PDestination implements TestForPartInnerInterface { 
			
            private PDestination(String whereTo) { 
               
            } 
			public void show() {
				System.out.println(234);
			} 
        } 
        return new PDestination(s); 
		
	}
	
	/**	局部内部类定义在作用域内 **/
	{
		boolean flag = true;
		if(flag){
			class hello{
				
			}
		}
		
	}
	
	
	
	
}
