package learn.thread.testFoundation;


/**
 * 发布对象
 * 发布某对象时，可能会间接发布其他对象
 * @author Administrator
 *
 */
public class TestPublishObj {
	// 发布方式1 作为非私有域发布
	public static Object bePublish = null;
	public Object bePublish1 = null;
	
	// 发布方式2， 作为非私有方法返回
	public Object get() {
		Object bePublish2 = new Object();
		return bePublish2;
	}
	
	public static void main(String[] args) {
		Object bePublish3 = new Object();
		// 发布方式3, 作为参数发布
		doPublish(bePublish3);
	}
	
	public static void doPublish(Object obj) {}
}