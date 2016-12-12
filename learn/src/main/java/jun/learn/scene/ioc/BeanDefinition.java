package jun.learn.scene.ioc;

public interface BeanDefinition {
	/**
	 * Return the current bean class name of this bean definition.
	 * <p>Note that this does not have to be the actual class name used at runtime, in
	 * case of a child definition overriding/inheriting the class name from its parent.
	 * Hence, do <i>not</i> consider this to be the definitive bean type at runtime but
	 * rather only use it for parsing purposes at the individual bean definition level.
	 */
	String getBeanClassName();

	/**
	 * Override the bean class name of this bean definition.
	 * <p>The class name can be modified during bean factory post-processing,
	 * typically replacing the original class name with a parsed variant of it.
	 */
	void setBeanClassName(String beanClassName);

	/**
	 * Specify the factory bean to use, if any.
	 */
	void setFactoryBeanName(String factoryBeanName);

	/**
	 * Return a factory method, if any.
	 */
	String getFactoryMethodName();

	/**
	 * Specify a factory method, if any. This method will be invoked with
	 * constructor arguments, or with no arguments if none are specified.
	 * The method will be invoked on the specified factory bean, if any,
	 * or otherwise as a static method on the local bean class.
	 * @param factoryMethodName static factory method name,
	 * or {@code null} if normal constructor creation should be used
	 * @see #getBeanClassName()
	 */
	void setFactoryMethodName(String factoryMethodName);

	/**
	 * Return the bean names that this bean depends on.
	 */
	String[] getDependsOn();

	/**
	 * Set the names of the beans that this bean depends on being initialized.
	 * The bean factory will guarantee that these beans get initialized first.
	 */
	void setDependsOn(String... dependsOn);
}
