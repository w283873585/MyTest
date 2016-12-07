package jun.learn.foundation.generic;

public class Holder<T> {
	private T a;
	public Holder(T a) {
		this.a = a;
	}
	
	public void set(T a) {
		this.a = a;
	}
	
	public T get() {
		return a;
	}
	
	
	public static void main(String[] args) {
		Holder<String> h = new Holder<String>("String");
		h.set("helloWorld");
		System.out.println(h.get());
	}
}
