package learn.base;

public class ClassInitialize {
	private static ClassInitialize one = new ClassInitialize();
	private static int x;
	private static int y = 2;
	private String str = "1245";
	
	{
		System.out.println("dynamic module loading");
	}
	
	static {
		System.out.println("static module loading");
	}
	
	public ClassInitialize() {
		System.out.println("a constructor function");
		x++;
		y++;
	}
	
	public static void main(String[] args) {
		new ClassInitialize();
/**
 * 构造函数构造流程（如果有父类则先初始化父类）
 * 1.为静态作用域分配内存空间，并初始化值
 * 2.依次执行静态语句（包括静态域的赋值语句）
 * 3.依次执行动态代码块(包括作用域赋值语句)
 * 4.执行构造函数
 * 5.完成构造
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
	3.初始化：为类的静态变量赋予正确的初�贾�
 
 	Java虚拟机主动使用一个类的六种情况
	1.创建类的实例
	2.访问某个类或接口的静态变量，或者对该静态变量赋值
	3.调用类的静态方法
	4.反射
	5.初始化一个类的子类
	6.Java虚拟机启动时被标明为启动类的类
 */		
	}
}
