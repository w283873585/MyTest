package jun.learn.foundation.base;

public class CodeReuse {
	/**
	 * 代码复用：
	 *	1. 继承:
	 * 		复用接口
	 *	2. 组合：
	 * 		复用功能
	 */
	/**
	 * 组合复用
	 */
	public class Car{
		private Engine engine = new Engine();
		/**
		 * 组合依赖
		 * 		组合对象也可自由组装，依赖对象的接口
		 */
		public void _run() {
			// 内部引擎开始工作
			engine.start();
		}
		
		/**
		 * 临时依赖，
		 * 		需要自己创建对象，会依赖具体对象
		 */
		public void run() {
			Engine engine = new Engine();
			// 临时创建一个引擎进行工作
			engine.start();
		}
		
		/**
		 * 参数依赖
		 * 		可灵活传入，依赖参数对象的接口
		 */
		public void run(Engine engine) {
			// 临时引入外部引擎进行工作
			engine.start();
		}
	}
	
	/**
	 * 继承复用
	 */
	public class FoolishCar extends Engine{
		/**
		 * 强依赖具体接口
		 */
		public void run() {
			this.start();
		}
	}
	
	public class Engine{
		public void start() {
			System.out.println("starting work");
		}
	}
}
