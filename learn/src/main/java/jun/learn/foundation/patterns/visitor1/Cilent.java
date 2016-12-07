package jun.learn.foundation.patterns.visitor1;

public class Cilent {
	public static void main(String[] args) {
		ZhangFeiXiaoDi xjr = new ZhangFeiXiaoDi(18, "jianren");
		
		// 孙权出场
		SunQuan sunQuan = new SunQuan();
		// 吃客出场
		Assassin as = new Assassin();
		
		// 内奸开始行动
		xjr.accpet(sunQuan);
		
		xjr.accpet(as);
		
		
		/*
		 * 访问者模式，貌似重点在  内奸与访问者的配合，
		 * 在目标毫不知觉的情况下暴露目标信息， (目标需不需要知觉，貌似不太清楚，但是目标不需要配合，只需要内奸配合就ok了，解决了访问者与目标的耦合)
		 * 让访问者可以做出相应动作
		 * 牛逼的功能,  访问复杂结构体 (附加的见解，要访问复杂的机构体才有意义, )
		 * 在书上有拿迭代器模式与访问者模式对比，迭代器只能迭代同等级继承层次的对象，访问器可以访问任意符合规范的对象。
		 * 这说明 访问者与迭代器 是有一定的共同点的，用来访问多个对象，
		 * 如果仅仅访问一个简单的对象，就不需要访问者了，对象应该可以自己提供服务
		 * 换一种思维，
		 * 一个对象提供一种服务是很方便的，(这里是需要暴漏自身的一些数据的)
		 * 如果是很多对象呢，
		 * 当提供很多相同的服务时，可能还可以遍历执行，然后再统计，(但这其实限制了一些灵活性)
		 * 当很多对象提供不同的服务，根本就不能遍历处理，
		 * 这时候一一调用就不那么方便了
		 * 这时候提供一个访问者，   将需要提供服务的一些方法暴漏给访问者， 让访问者统一处理，
		 * 是不是挺方便
		 */
	}
}
