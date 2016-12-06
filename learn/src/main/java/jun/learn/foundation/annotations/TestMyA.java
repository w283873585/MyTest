package jun.learn.foundation.annotations;

public class TestMyA {
	// value元素才可以省略键
	@MyA("123")
	public String toHello() {
		return "123";
	}
}
