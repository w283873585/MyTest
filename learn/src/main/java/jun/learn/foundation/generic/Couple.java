package jun.learn.foundation.generic;

public class Couple<A, B> {
	public final A a;
	public final B b;
	public Couple(A a, B b) {
		this.a = a;
		this.b = b;
	}
	
	public static void main(String[] args) {
		
		Couple<String, Integer> c = new Couple<String, Integer>("helloWorld", 123);
		System.out.println(c.a + ":  " + c.b);
		// ����final����ȷʵ�ܱ���publicԪ�أ��ڶ��󱻹����Ժ�finalԪ�ؾͲ��ܱ�������ֵ�ˡ�
		// ����û��Υ��java��̵İ�ȫ����
	}
}
