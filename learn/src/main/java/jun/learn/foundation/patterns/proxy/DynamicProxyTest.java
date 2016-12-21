package jun.learn.foundation.patterns.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class DynamicProxyTest {

	public static class ProxyHandler implements InvocationHandler{
		public ProxyHandler(Object realObj) {
			this.obj = realObj;
		}
		
		private Object obj;
		
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			Object result = method.invoke(this.obj, args);  
			return result;
		}
	}
	
	public interface ISubject{
		public void say(String msg);
	}
	
	public interface Animal {
		public void run();
	}
	
	public static class Subject implements ISubject, Animal{
		public void say(String msg) {
			System.out.println("i can speak " + msg);
		}

		@Override
		public void run() {
			System.out.println("animal is running");
		}
	}
	
	public static void main(String[] args) {
		ISubject realObj = new Subject();
		
		/**
		 * 	动态代理：
		 *	Proxy.newProxyInstance静态工厂生成一个代理对象, 
		 *	代理对象是面向接口的, (即第二个参数, 生成的代理对象可强转为接口类型)
		 *	hanler.invoke则是代理的执行策略. (劫持所有方法调用)
		 */
		ISubject proxy = (ISubject) Proxy.newProxyInstance(
					realObj.getClass().getClassLoader(), 
					realObj.getClass().getInterfaces(), 
					new ProxyHandler(realObj));
		
		((Animal) proxy).run();
		
		proxy.say("hello world");
	}
}
