package jun.learn.scene.lifecycle;

public class Client {
	
	@SuppressWarnings("null")
	public static void main(String[] args) {
		
		/**
		 * 	被监听者(发布者)
		 *  可以通过listener.lifecycleEvent(event)向监听者(订阅者)发布通知
		 *  LifecycleEvent作为数据对象存在
		 */
		Lifecycle lifecycle = null;
		
		LifecycleListener listener = null;
		lifecycle.addLifecycleListener(listener);
		lifecycle.addLifecycleListener(listener);
	}
}
