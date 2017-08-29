package jun.learn.scene.wrapped;

/**
 * Session对象的包装类。
 * 主要是让Session提供禁用属性
 */
public class Wrapper<T>{
	private T t;
	private boolean disabled = false;
	
	public static <T>Wrapper<T> wrap(T t) {
		return new Wrapper<T>(t);
	}
	
	private Wrapper(T t) {
		this.t = t;
	}
	
	public void disable() {
		t = null;
		disabled = true;
	}
	
	public void send(String msg) {
		if (disabled) return;
	}
}