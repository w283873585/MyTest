package jun.learn.foundation.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface UserCase {
	
	/**
	 * 注解元素可用的类型如�?
	 * �?��基本类型
	 * String
	 * Class
	 * enum
	 * Annotation
	 * 以上类型的数�?
	 */
	
	/*
	 * 
	 * 默认值限�?
	 * 编译器对元素的默认�?有些过分的限�?
	 * 首先元素不能有不确定的�?
	 * 也就是说，元素必须要么具有默认�?，要么在使用注解时提供元素的�?
	 * 
	 * 其次对于非基本类型的元素�?
	 * 无论是在源码中声明，
	 * 或�?设置默认�?
	 * 都不能为null
	 */
	
	public int id();
	public String description() default "no description";
}