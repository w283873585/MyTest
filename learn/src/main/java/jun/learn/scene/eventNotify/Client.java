package jun.learn.scene.eventNotify;

public class Client {
	public static void main(String[] args) {
		/**
		 * 首先是产生一个转发器， 作为定制者的发布方， 
		 * 
		 * 接着将各种定制者注册到转发器上，
		 * 
		 * 然后新建一个管理产品的管理者，
		 * 
		 * 管理者的所有动作会产生事件， 
		 * 
		 * 事件将作为转发器的发布者， 并立即通知转发器转发。
		 * 
		 * 		generate 	notify     	  notify
		 * 管理者 ------> 事件 ------> 转发器 ------> 定制者
		 * 
		 * 事件容易与定制者会过度耦合， 所以转发器其实扮演的是中介者的角色。
		 */
		
		EventDispatch dispatch = EventDispatch.getEventDispathc();
		
		dispatch.registerCustomer(new Begger());
		
		dispatch.registerCustomer(new Commoner());
		
		dispatch.registerCustomer(new Nobleman());
		
		ProductManager factory = new ProductManager();
		
		System.out.println("=====模拟创建产品事件=====");
		System.out.println("创建一个小孩子的原子弹");
		Product p = factory.createProduct("小孩子原子弹");
		
		System.out.println("=====模拟修改产品事件=====");
		System.out.println("修改一个小孩子的原子弹为小胖子原子弹");
		factory.editProduct(p, "小pang子原子弹");
		
		System.out.println("=====模拟克隆产品事件=====");
		System.out.println("克隆胖子原子弹");
		factory.clone(p);
		
		System.out.println("=====模拟yiqi产品事件=====");
		System.out.println("yiqi胖子原子弹");
		factory.abandonProduct(p);
	}
}
