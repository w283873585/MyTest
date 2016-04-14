package learn.atest;


public class TestClassFrom {
	public static void main(String[] args) {
		Class<String> s = String.class;
		System.out.println("".getClass() == String.class);
		System.out.println(Class.class);
	}
}
