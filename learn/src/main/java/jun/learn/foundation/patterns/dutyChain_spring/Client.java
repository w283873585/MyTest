package jun.learn.foundation.patterns.dutyChain_spring;

public class Client {
	
	public static class OneInterceptor implements Interceptor{
		@Override
		public Object invoke(Invocation i) {
			System.out.println("我只来刷一波存在感");
			return i.proceed();
		}
	}
	
	public static class MatchedInterceptor implements Interceptor, InterceptorAndDynamicMethodMatcher{
		@Override
		public Object invoke(Invocation i) {
			System.out.println("do something customized");
			return i.proceed();
		}

		@Override
		public boolean matchs(Object target) {
			return false;
		}
	}
	
	public static class MyInvocation implements Invocation{

		private int index = -1;
		private Interceptor[] chain;
		private Object target = null;
		
		public MyInvocation(Interceptor[] chain,  Object target) {
			this.chain = chain;
			this.target = target;
		}
		
		@Override
		public Object proceed() {
			Interceptor current = chain[++index];
			if (current instanceof InterceptorAndDynamicMethodMatcher) {
				if (((InterceptorAndDynamicMethodMatcher) current).matchs(target))
					return current.invoke(this);
				else {
					return proceed();
				}
			} else {
				return current.invoke(this);
			}
		}
	}
	
	
	public static void main(String[] args) {
		
		/**
		 * 该场景为典型的"责任链上下文"实现.
		 * Invocation则作为上下文的角色出镜.
		 * 
		 * Invocation只有一个迭代执行责任链的方法->proceed, (当然还有一些上下文的基础功能->获取相关的上下文信息)
		 * 中间插入了一个特别的抽象层, InterceptorAndDynamicMethodMatcher, 作为一个匹配策略参与了链式流程.
		 * 
		 */
		
		Interceptor[] chain = new Interceptor[3];
		chain[0] = new OneInterceptor();
		chain[1] = new MatchedInterceptor();
		chain[2] = new OneInterceptor();
		
		Object target = new Object();
		
		Invocation in = new MyInvocation(chain, target);
		in.proceed();
	}
}
