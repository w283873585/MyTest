package jun.learn.foundation.enums;

import java.util.Random;


public enum EnumB implements Generator<EnumB>{
	CAR, PLANE, TRAIN;

	
	private Random rand = new Random(47);
	@Override
	public EnumB next() {
		return values()[rand.nextInt(values().length)];
	}
	// EnumB 为final类不能被继承
	// 也不能继承，因为它已经继承了Enum
	// 但可以实现多接口
	
	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			System.out.println(EnumB.CAR.next());
		}
	}
	// 这个结果有点奇�?，不过你必须要有�?��实例才能调用其上的方�?
}
