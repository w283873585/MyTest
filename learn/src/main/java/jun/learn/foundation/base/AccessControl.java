package jun.learn.foundation.base;

public class AccessControl {
	/**
	 * 类访问权限管理：
	 * 	1. 每个编译单元（文件）都只能有一个public类。
	 *	2. public类的名称必须和含有该编译单元的文件名一模一样。
	 *	3. 编译单元的文件可完全不带public类
	 *	4. 类不存在protected访问权限
	 */
	public static class Car{
		/**
		 * 默认构造器：
		 * 		1. 如果没有明确地创建一个构造器，系统会自动创建一个默认构造器（无参）
		 * 		2. 如果明确的创建了构造器，默认构造器将不会被创建
		 */
		private Car() {
			/**
			 * 构造函数为隐式static方法。
			 * 
			 * 当构造函数为private时，
			 * 通常用来限制外部手动构建类
			 * 通常会通过类静态方法去创建对象。
			 */
		}
		
		/**
		 * 当前类对外开放的接口，
		 * 任何人可以调用
		 */
		public void run() {
			System.out.println("go go go");
		}
		
		/**
		 * 用于暴露给子类的方法
		 * 常用于子类定制特定行为
		 * 注意： 也提供包访问权限
		 */
		protected Car r(Car c) {
			System.out.println("look out");
			return this;
		}
		
		/**
		 * 包访问权限，
		 * 说明该方法只能暴露给当前类库同一包下的其他类
		 */
		void sing() {
			doSomething();
		}
		
		/**
		 * 内部子程序,只用于内部调用,增加类的内聚性
		 */
		private void doSomething() {
			/**
			 * 私有方法，内部默认为final方法
			 * 不可被重写
			 */
		}
	}
	
	public static class FordCar extends Car{
		@Override
		public void run() {
			System.out.println("i can go anywhere");
		}
		
		/**
		 * 子类可以加大控制权限（参数完全不能改变）
		 * private < 包内 < protected < public
		 */
		@Override
		public FordCar r(Car c) {
			/**
			 * 重写时：
			 * 		1. 返回参数可以缩小范围（即用子类替代）
			 * 		2. 访问权限可以放宽
			 * 		3. 参数完全不能变
			 * 		4. final方法不能被重写
			 */
			return this;
		}
		
		/**
		 * 子类可以加大控制权限
		 * private < 包内 < protected < public
		 */
		protected void sing() {
			
		}
	}
}

