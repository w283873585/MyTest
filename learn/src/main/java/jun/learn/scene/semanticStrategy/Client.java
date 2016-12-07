package jun.learn.scene.semanticStrategy;

public class Client {
	public static void main(String[] args) {
		String expression = "helloWorld";
		
		final BeanFactory factory = new BeanFactory();
		final Scope scope = new Scope();
		
		BeanExpressionResolver resolver = new BeanExpressionResolver() {

			@Override
			public Object evaluate(String expression) {
				Object value = factory.getBean(expression);
				
				if (value == null) {
					value = scope.resolve(""); 
				}
				
				if (value == null) {
					System.out.println("没找到");
				} else {
					System.out.println("已找到" + value);
				}
				
				return value;
			}
		};
		
		resolver.evaluate(expression);
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
	
	public interface BeanExpressionResolver {
		Object evaluate(String value) ;
	}
}
