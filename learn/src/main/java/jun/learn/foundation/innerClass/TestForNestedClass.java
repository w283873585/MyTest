package jun.learn.foundation.innerClass;

public class TestForNestedClass {
	/**
		嵌套类和普通的内部类还有一个区别：普通内部类不能有static数据和static属性，也不能包含嵌套类，但嵌套类可以
	 */
	
	private static String name = "��Χ��";
	private static int count = 0;
	
	public static class NestedClass{
		/**
		 * 静态内部类，也叫做嵌套类
		 * 可访问外围类所有静态成员
		 * 与外围类有着捆绑的关联,访问不到外围实例的相关属性	 （有类似静态域的相关特征）
		 * 其他性质与普通类无异
		 */
		static{
			System.out.println(name);
			count++;
		}
		public void show(){
			
			System.out.println("我能干很多事");
		}
		public NestedClass(String str){
			System.out.println(str);
		}
	}
	
	
	public static void main(String[] args) {
		/* 可做为正常的类使用	*/
		NestedClass nc = new TestForNestedClass.NestedClass("进入构造函数");
		nc.show();
		System.out.println(TestForNestedClass.count);
	}
}
