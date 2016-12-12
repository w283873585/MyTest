package jun.learn.scene.ioc;

import java.util.HashMap;
import java.util.Map;

public class ApplicationContext implements BeanFactory, BeanDefinitionRegistry{

	/**	beanName-BeanDefinition	*/ 
	private Map<String, BeanDefinition> beanDefinitions = new HashMap<String, BeanDefinition>();
	
	/**	beanName-Bean */ 
	private Map<String, Object> beanCache = new HashMap<String, Object>();
	
	@Override
	public void registerBeanDefinition(String beanName, BeanDefinition beanDefinition) {
		this.beanDefinitions.put(beanName, beanDefinition);
	}

	@Override
	public void removeBeanDefinition(String beanName) {
		this.beanDefinitions.remove(beanName);
	}

	@Override
	public BeanDefinition getBeanDefinition(String beanName) {
		return this.beanDefinitions.get(beanName);
	}

	@Override
	public boolean containsBeanDefinition(String beanName) {
		return this.beanDefinitions.containsKey(beanName);
	}

	@Override
	public String[] getBeanDefinitionNames() {
		return (String[]) this.beanDefinitions.keySet().toArray();
	}

	@Override
	public int getBeanDefinitionCount() {
		return this.beanDefinitions.size();
	}

	@Override
	public boolean isBeanNameInUse(String beanName) {
		return containsBeanDefinition(beanName);
	}

	@Override
	public Object getBean(String name) {
		return null;
	}

	@Override
	public <T> T getBean(String name, Class<T> requiredType) {
		return doGetBean(name, requiredType);
	}

	@Override
	public <T> T getBean(Class<T> requiredType) {
		return doGetBean(null, requiredType);
	}
	
	protected <T> T createBean(String name, Class<T> requiredType) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	protected <T> T doGetBean(String name, Class<T> requiredType) {
		Object bean = null;
		if (beanCache.containsKey(name))
			bean = beanCache.get(name);
		else 
			bean = createBean(name, requiredType);
		return (T) bean;
	}
}
