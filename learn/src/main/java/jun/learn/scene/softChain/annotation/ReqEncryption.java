package jun.learn.scene.softChain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  请求约束
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface ReqEncryption {
	
	boolean requestEncrypt() default true;
	
	boolean responseEncrypt() default true;
}
