package jun.learn.foundation.patterns.visitor2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Client2 {
	
	// 田背村，新田大队某日的人员状况
	public static List<Human> getAll() {
		Random rand = new Random(47);
		List<Human> result = new ArrayList<Human>();
		for (int i = 0; i < 50; i++) {
			if (rand.nextBoolean()) {
				result.add(new Male(rand.nextBoolean(),rand.nextBoolean()));
			} else {
				result.add(new Female(rand.nextBoolean(),rand.nextBoolean()));
			}
		}
		return result;
	}
	
	public static void accpet(Visitor v) {
		for (Human human : getAll()) {
			human.accpet(v);
		}
	}
	
	public static void main(String[] args) {
		
		// 计数访问者
		Counter visitor = new Counter();
		
		// 接受访问者的访问
		accpet(visitor);
		
		// 访问者提交统计
		visitor.report();
		
		/**
		 * 在这个场景，如果没有访问者，就只能一一调用自身的方法再统计
		 * 访问者对目标是很了解的，这是该模式的先决条件
		 * 
		 * 容易扩展的点: 
		 * 公分计算，（因为改变不会影响目标的元数据）
		 * 添加Human种类， 如人妖， 只需要在visitor接口添加处理人妖的接口就可以了
		 * 具体human如male添加数据,计分者需要变更的地方也很少
		 * 基于元数据做其他的扩展统计, 则添加新的访问者，访问者封装了 处理元数据的业务逻辑，缩小了模块粒子度，(即使模块更小了，抽象层次更高了，抽象了元数据的一些业务逻辑行为，可增加代码复用性， 中间层)
		 * 更 解耦了客户端与目标的关系， (中间层，解耦合),
		 * 还可以更灵活的处理 访问者和目标的交互
		 * 
		 */
	}
}
