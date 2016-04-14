package learn.annotation;

import java.lang.annotation.Annotation;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;


@Target({ElementType.TYPE,
	ElementType.ANNOTATION_TYPE,
	ElementType.CONSTRUCTOR,
	ElementType.FIELD,
	ElementType.LOCAL_VARIABLE,
	ElementType.METHOD,
	ElementType.PACKAGE,
	ElementType.PARAMETER})
public @interface Magic {
	// 注解的方法相当于注解内部域，在其他类使用注解时传递的参数对应这些方法域
	// 默认的方法，value方法域
	// 这些方法域在没有default的情况下是必填的，有default则选填.
	public String value();
	public String id() default "magic";
	
	
	// 方法域可用的元素类型有，
	// 1. 所有基本类型，
	// 2. String 
	// 3. Class
	// 4. enum
	// 5. Annotation
	// 6. 以上类型的数组
	
	
	// 在只有value方法域需要填时，可省略value=
	@Magic("helloword")
	class Hello{}
	@Magic(value="world", id="helloworld")
	class World{}
	
	
	// 编写注解处理器
	class AnnotationHandler{
		// 打印相应的注解信息
		public void invoke(Class<?> clazz) {
			// 首先拿到类上面的注解信息
			Annotation[] annos = clazz.getAnnotations();
			for (Annotation anno : annos) {
				Magic m = (Magic) anno;
				System.out.print(m.id());
				System.out.println(m.value());
				System.out.println(m.annotationType());
				Class<? extends Magic> class_m = m.getClass();
			}
		}
		
		public void printAnnoInfo(Class<?> clazz) {
			// 首先拿到类上面的注解信息
			Annotation[] annos = clazz.getAnnotations();
			for (Annotation anno : annos) {
				Class<?> class_m = anno.getClass();
				System.out.println(class_m.getDeclaredMethods());
			}
		}
		
		public static void main(String[] args) {
			new AnnotationHandler().printAnnoInfo(Magic.class);
		}
	}
}
