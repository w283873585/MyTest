package jun.learn.foundation.base;

/**
 * 多态
 */
public class Polymorphic {
	public static class FordCar extends Car{
		@Override
		public void run() {
			System.out.println("ford: i can fly");
		}
	}
	
	public static class BMWCar extends Car{
		@Override
		public void run() {
			System.out.println("bmw: i can bb");
		}
	}
	
	public static abstract class Car{
		public abstract void run();
	}
	
	public static class Driver {
		public void drive(Car car) {
			car.run();
		}
	}
	
	public static void main(String[] args) {
		/**
		 * final方法不能重写，所以不存在多态
		 * 私有方法（隐式final）也不能多态，
		 * 域也不能多态
		 * 
		 * 只有普通方法有多态特性。
		 */
		Driver x = new Driver();
		Car fordCar = new FordCar();
		Car bmwCar = new BMWCar();
		x.drive(fordCar);
		x.drive(bmwCar);
	}
}
