package jun.learn.scene.ioc;

public interface BeanFactory {
	
	public Object getBean(String name);
	
	<T> T getBean(String name, Class<T> requiredType);
	
	<T> T getBean(Class<T> requiredType);
}
