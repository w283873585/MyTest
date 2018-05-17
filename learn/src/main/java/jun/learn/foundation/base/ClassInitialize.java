package jun.learn.foundation.base;

public class ClassInitialize {
	public static class FordCar extends Car{
		/**
		 * 静态域初始化	-> 	clinit	-> 引用默认为null, 数值为0
		 * 动态域初始化  ->	init	-> 引用默认为null, 数值为0
		 */
		public int horse;
		public static FordCar ford = new FordCar();
		/**
		 * 如果静态初始存在构造新的对象，则先执行构造流程
		 * 得到流程是	1 3456 23456
		 */
		static {
			System.out.println("2");
		}
		{
			System.out.println("5");
		}
		public FordCar() {
			System.out.println("6");
		}
	}
	
	public static class Car {
		static {
			System.out.println("1");
		}
		{
			System.out.println("3");
		}
		public Car() {
			System.out.println("4");
		}
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		/**
		 * 触发FordCar类初始化
		 */
		Class.forName("jun.learn.foundation.base.ClassInitialize$FordCar");
		System.out.println("------------分割线------------------------");
		
		/**
		 * 1, 2步骤属于类初始化步骤，
		 * 如果两个类在之前就有初始化, 则不会输出
		 */
		System.out.println(new FordCar());		// 123456
	}
/**
 * 对象构造流程
 * 1. 父类静态块执行（包括静态域的赋值语句）
 * 2. 子类静态块执行（包括静态域的赋值语句）
 * 3. 父类动态块执行(包括作用域赋值语句)
 * 4. 父类构造函数执行
 * 5. 子类动态块执行(包括作用域赋值语句)
 * 6. 子类构造函数执行
 */
	
/**
 * jvm内部
 * 		父类clinit方法执行	-- 属于类初始化
 * 		子类clinit方法执行	-- 属于类初始化
 * 		父类init方法执行	-- 属于构造过程
 * 		子类init方法执行	-- 属于构造过程
 */
	
/*
	类加载分为：
		根类加载器，
		扩展类加载器，
		系统类加载器，
		自定义加载器，继承ClassLoader
	类加载器加载流程
	1.加载：查找并加载类的二进制数据
	2.连接
		2.1：确保被加载类的正确性
		2.2：为类的静态变量分配内存，并将其初始化为默认值
		2.3：把类中的符号引用转换为直接引用
	3.初始化：为类的静态变量赋予正确的初始值
 
 	Java虚拟机主动使用一个类的六种情况
		1.创建类的实例
		2.访问某个类或接口的静态变量，或者对该静态变量赋值
		3.调用类的静态方法
		4.反射
		5.初始化一个类的子类
		6.Java虚拟机启动时被标明为启动类的类
 */		
}
