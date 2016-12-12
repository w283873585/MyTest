package jun.learn.scene.ioc;

public interface BeanDefinitionRegistry {
	
	void registerBeanDefinition(String beanName, BeanDefinition beanDefinition);
	
	void removeBeanDefinition(String beanName);
	
	BeanDefinition getBeanDefinition(String beanName);
	
	boolean containsBeanDefinition(String beanName);
	
	String[] getBeanDefinitionNames();
	
	int getBeanDefinitionCount();
	
	boolean isBeanNameInUse(String beanName);
}
