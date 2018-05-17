package jun.learn.foundation.base;

public interface Interface {
	/**
	 * 接口继承接口可以用来扩展接口
	 */
	public interface Car extends Machine{
		/**
		 * 隐式为static final类型, 默认为包访问权限
		 * 所以必须显式初始化
		 */
		int count = 1;		
		/**
		 * 自动变为public访问权限
		 */
		void run();
	}
	
	public interface Machine{
		void run();
	}
	
	/**
	 * 接口可实现多个，
	 */
	public class FordCar implements Car, Machine{
		@Override
		public void run() {
			System.out.println(Car.count);
		}
	}
}
