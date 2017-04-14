package jun.learn.foundation.innerClass;


public class TestForInnerClass {
	
	private static String name = "外围类";
	private void show(){
		
		System.out.println("show something");
	}
	
	/**
	 * 个人推荐使用getxxx()来获取成员内部类，尤其是该内部类的构造函数无参数时
	 * @return
	 */
	public InnerClass getInner(){
		return new InnerClass();
	}
	/**
	 * 成员内部类
	 */
	private class InnerClass{
		/**
		 * 内部类是绑定在外围类实例上的
		 * 它可访问外围类所有的属性
		 * 它也属于外围类的成员
		 * 成员内部类不能含有static的变量和方法,代码块
		 */
		{
			System.out.println(name);
			TestForInnerClass.this.show();
		}
	}
	
	
	public static void main(String[] args) {
		TestForInnerClass tfc = new TestForInnerClass();
		InnerClass ic = tfc.getInner();
		/** 在拥有一定权限时，可以以  .new 的方式实例成员内部类 **/
		InnerClass ic1 = tfc.new InnerClass();
		
	}
	
}
