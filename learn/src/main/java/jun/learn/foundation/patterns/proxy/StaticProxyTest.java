package jun.learn.foundation.patterns.proxy;

public class StaticProxyTest {

	public interface ISubject{
		public void say(String msg);
	}
	
	public static class Subject implements ISubject{
		public void say(String msg) {
			System.out.println("i can speak " + msg);
		}
	}
	
	public static class ProxySubject implements ISubject{
		private ISubject subject;
		
		public ProxySubject(ISubject subject) {
			this.subject = subject;
		}
		
		@Override
		public void say(String msg) {
			// do something before real invocation
			subject.say(msg);
			// do something after real invocation
		}
	}
	
	public static void main(String[] args) {
		
		/**
		 * 静态代理
		 * 
		 * 同继承体系, 劫持方法调用(委托)
		 * 
		 * 作用:　隐藏真实对象，加工或改变某些行为．
		 */
		ISubject subject = new Subject();
		
		ISubject proxySubject = new ProxySubject(subject);
		
		proxySubject.say("hello world");
	}
}
