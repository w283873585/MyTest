package learn.client.semanticStrategy;

public class Client_t {
	public static void main(String[] args) {
		String expression = "helloWorld";
		
		BeanFactory factory = new BeanFactory();
		Scope scope = new Scope();
		
		BeanExpressionResolver resolver = new BeanExpressionResolver() {

			@Override
			public Object evaluate(String expression, BeanExpressionContext context) {
				return context.getObject(expression);
			}
		};
		
		resolver.evaluate(expression, new BeanExpressionContext(factory, scope));
	}
	
	public static class BeanFactory {
		public Object getBean(String string) {
			return null;
		}
	}
	
	public static class Scope {
		public Object resolve(String string) {
			return null;
		}
	}
	
	/** 
	 * 表达式解析的策略
	 * 语义上区别, 一个是带有上下文的策略, 另一个是没有上下文.
	 * 有上下文的策略, 即表示该策略与上下文息息相关, 脱离上下文则策略不完整.
	 */
	public interface BeanExpressionResolver {
		Object evaluate(String value, BeanExpressionContext context) ;
	}
	
	public static class BeanExpressionContext{
		private final BeanFactory beanFactory;

		private final Scope scope;


		public BeanExpressionContext(BeanFactory beanFactory, Scope scope) {
			this.beanFactory = beanFactory;
			this.scope = scope;
		}

		public final BeanFactory getBeanFactory() {
			return this.beanFactory;
		}

		public final Scope getScope() {
			return this.scope;
		}


		public Object getObject(String key) {
			Object value = this.beanFactory.getBean(key);
			if (value != null) {
				return value;
			}
			if (this.scope != null){
				return this.scope.resolve(key);
			}
			else {
				return null;
			}
		}
	}
}
