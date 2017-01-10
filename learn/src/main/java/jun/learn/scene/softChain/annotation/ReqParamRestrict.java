package jun.learn.scene.softChain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *  请求约束
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Repeatable(ReqParamRestricts.class)
public @interface ReqParamRestrict {
	
	String key();
	
	ReqParamRestrictType[] value() default ReqParamRestrictType.noEmpty;
}
