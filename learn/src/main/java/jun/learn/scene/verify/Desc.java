package jun.learn.scene.verify;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;

/**
 * 该描述注解可修饰如下元素：<br>
 * 1. 请求对象属性<br>
 * 2. 响应对象属性<br>
 * 3. 响应码枚举属性<br>
 * 4. 接口请求方法<br>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE, ElementType.METHOD})
public @interface Desc {
	
	/**
	 * 描述信息相关<br>
	 * 1. 接口描述信息，<br>
	 * 2. 请求属性描述信息<br>
	 * 3. 响应属性描述信息<br>
	 * 4. 响应码描述信息
	 */
	public String value() default "";
	
	
	/**
	 * 该属性的某些限制, 用于请求参数验证<br><br>
	 * 【只在描述请求对象属性生效】
	 */
	public Limit limit() default Limit.none;
	
	
	/**
	 * 该属性的正则表达式限制, 用于请求参数验证, 优先级大于limit<br>
	 * regex与limit只会有一个生效<br><br>
	 * 【只在描述请求对象属性生效】
	 */
	public String regex() default "";
	
	
	/**
	 * 表示该属性可以不传, 用于请求参数验证, 优先级最大。<br>
	 * optional可与regex,limit同时使用. <br>
	 * 表示该属性要么为空，要么必须满足某限制<br><br>
	 * 【只在描述请求对象属性生效】
	 */
	public boolean optional() default false;
	
	
	/**
	 * 表示该接口不需要登录<br><br>
	 * 【只在描述接口描述信息生效】
	 */
	public boolean nologin() default false;
	
	/**
	 *	某些属性的限制
	 */
	public enum Limit {
		/**
		 * 不为空
		 */
		none {
			@Override
			public boolean matches(Object val) {
				return true;
			}
		},
		
		/**
		 * 不为空
		 */
		notNull {
			@Override
			public boolean matches(Object val) {
				return val != null;
			}
		},
		
		/**
		 * 日期
		 */
		date {
			@Override
			public boolean matches(Object val) {
				return val != null;
			}
		},
		
		/**
		 * 数值
		 */
		number {
			@Override
			public boolean matches(Object val) {
				return Pattern.matches("^[0-9]+$", val.toString());
			}
		};
		
		public abstract boolean matches(Object val);
	}
	
	
	@Desc
	public static class DescUtil {
		private static final Desc DEFAULT = DescUtil.class.getAnnotation(Desc.class);
		
		public static Desc getDesc(Field f) {
			return noNull(f.getAnnotation(Desc.class));
		}
		
		public static Desc getDesc(Class<?> f) {
			return noNull(f.getAnnotation(Desc.class));
		}
		
		public static Desc getDesc(Method f) {
			return noNull(f.getAnnotation(Desc.class));
		}
		
		private static Desc noNull(Desc desc) {
			if (desc == null) return DEFAULT;
			return desc;
		}
	}
}
