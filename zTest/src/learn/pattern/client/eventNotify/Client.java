package learn.pattern.client.eventNotify;

public class Client {
	public static void main(String[] args) {
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
