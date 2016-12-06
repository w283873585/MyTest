package jun.learn.foundation.enums;

public enum EnumA {
	APPLE("12345"){
		{
			System.out.println("come---->apple");
		}
		public String getInfo() {
			return "i am apple";
		}
		
		public void sayHello() {
			System.out.println("say hi to my world");
		}
		// The method customMethod() from the type new EnumA(){} is never used locally
		@SuppressWarnings("unused")
		public String customMethod() {
			return "this is custom method";
		}
	}, 
	PEAR(""){
		{
			System.out.println("come---->pear");
		}
		public String getInfo() {
			return "i am pear";
		}
	}, 
	BANANA{
		{
			System.out.println("come---->banana");
		}
		public String getInfo() {
			return "i am banana";
		}
	};
	static {
		System.out.println("静�?�码�?");
	}
	{
		System.out.println("动�?代码�?");
	}
	private String info;
	EnumA(String info) {
		this.info = info;
		System.out.println(info);
	}
	
	public abstract String getInfo();
	
	public void sayHello() {
		System.out.println("hello Wolrd");
	}
	
	// 构�?器默认为private
	EnumA() {}
	
	public static void main(String[] args) {
		Enum<EnumA> a = EnumA.APPLE;
		EnumA b = EnumA.APPLE;
		System.out.println("泛型和EnumA比较�?" + (a == b)); //true
		for (EnumA e : EnumA.values()) {
			System.out.println(e.ordinal()); // 序号
			System.out.println(e);
			System.out.println("myinfo is : ");
			System.out.println(e.getInfo());
			e.sayHello();
		}
		//  the result is:
		/* 
			动�?代码�?
			12345
			come---->apple
			动�?代码�?
			
			come---->pear
			动�?代码�?
			come---->banana
			静�?代码�?
			APPLE
			myinfo is : 
			i am apple
			say hi to my world
			PEAR
			myinfo is : 
			i am pear
			hello Wolrd
			BANANA
			myinfo is : 
			i am banana
			hello Wolrd
		*/
		
	}
	
	/*
	 * 《java编程思想�?中有这么�?���?
	 *  注意,如果你打算定义自己的方法，那么必须在enum实例序列的最后添加一个分号�?
	 *  同时，java要求你必须要定义enum实例�?
	 *  如果在定义enum实例之前定义了任何方法和属�?�?
	 *  那么编译就会得到错误信息
	 */
	
	/*
	 *  // �?��enum都继承于java.lang.Enum�?
	 * 	EnumA就像�?enum的实�?
	 *  它的'元素'就相当于  EnumA的实�?(在一些行为又想继承，如重写，重载，实现抽�?
	 *  EnumA在被类加载器加载时，
	 *  首先是EnmuA�?元素'会被�?��实例化，
	 *  在实例化前会先调用EnumA的动态代码块(与普通类实例的步骤相�?
	 *  '元素'就会传�?参数  ---> (�?元素'后面的括号中的参�?)参数必须与构造函数一�?���?
	 *  去调�?EnumA的私有构造器（构造器默认为private�?
	 *  然后执行'元素'内部的块代码
	 *  '元素'内不支持static�?(个人觉得enum'元素'就是静�?'元素')
	 *  �?��居然是调用位�?enum'元素'后定义的静�?代码�?
	 *  
	 *  enum支持抽象方法�?元素'内必须实现抽象方�?(与普通类相同)
	 *  EnumA'元素'只能调用  EnumA定义的方法，自己定义的私有方法永远不会被调用
	 *  
	 *  '元素'也是可以重写普�?方法
	 *  
	 */
}
