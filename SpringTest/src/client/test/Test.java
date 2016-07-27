package client.test;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class Test<T> {
	public Test() {
		Type type = getClass().getGenericSuperclass();
		System.out.println(type);
	}
	
	public static void main(String[] args) {
		new Test<String>();
		
		new B();
		
		System.out.println(((Type) B.class).getTypeName());
	}
	
	public static class B extends Test<String>{
		public B() {
			Type type = getClass().getGenericSuperclass();
			type = ((ParameterizedType) type).getActualTypeArguments()[0];
			Class<?> c = (Class) type;
			
			System.out.println(c);
		}
	}
}
