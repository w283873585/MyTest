package learn.atest;
/*
 * byte数组流
 * 对象流
 * 通道流
 * 文件流
 * 过滤流
 */
public class TestClassFrom {
	@SuppressWarnings("unused")
	public static void main(String[] args) throws InterruptedException {
		// 类型    引用	    对象
		Object obj = new Object();
		// 类型   引用
		Class<?> clazz = null;
		Class<String> s = String.class;
		System.out.println("".getClass() == String.class);
		
		/**
		Class.class Object.class String.class 是Class的实例
		获取Class类型的对象有三种方式：
		 	1. Class.forName("java.lang.String")
		 	2. String.class
		 	3. "".getClass()
		Class是个泛型类，该泛型类不能实例化。每个对象实例也是系统在加载类信息的时候主动创建的。（并且放在方法区内存中）如Class.class, String.class, Object.class，
		Class是个final类。不能扩展。
		Class就像提供了一个接口，可以获取自身的类型信息
		所有的普通对象都是根据class对象创建出来的。
		
		Object类，是所有类的基类。
		Object.class -> 对应的Class对象
		Object obj = new Object();
		obj.getClass() -> 实例可以通过getClass方法获取对应的Class对象
		
		
		
		*/
	}
}