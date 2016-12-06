package jun.learn.foundation.annotations;

import java.lang.annotation.*;

// 定义注解使用的地�?或一个方法或者一个作用域)
@Target(ElementType.METHOD)
// 用来定义在哪个级别可用， 在源代码�?SOURCE)类文件中(CLASS)或�?运行�?RUNTIME)
@Retention(RetentionPolicy.RUNTIME)
// 没有元素的注解称为标记注解，
public @interface Test {
	
}