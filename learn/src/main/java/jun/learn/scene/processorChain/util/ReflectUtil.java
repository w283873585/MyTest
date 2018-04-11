package jun.learn.scene.processorChain.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Map;
import java.util.Set;

import jun.learn.scene.verify.StringUtil;


/**
 * 反射的工具类。
 */
public class ReflectUtil {
	
	/**
	 * 从方法中获取第一个参数类型为T的自雷
	 */
	public static Class<?> getParamFromMethod (Method method, Class<?> t) {
		for (Class<?> clazz : method.getParameterTypes()) {
			if (t.isAssignableFrom(clazz)) {
				return clazz;
			}
		}
		return null;
	}
	
	/**
	 *	遍历class对象属性的消费者
	 */
	public static interface Consumer<T>{
		public boolean apply(String name, Field f, T t);
	}
	
	
	/**
	 * @return 
	 * 
	 */
	public static Object newIntance(Class<?> c) {
		Object newObj = null;
		try {
			newObj = c.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			e.printStackTrace();
		}
		return newObj;
	}
	
	/**
	 * 将val注入到target的f属性中
	 */
	public static void fillField(Field f, Object target, Object val) {
		try {
			f.setAccessible(true);
			f.set(target, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 将val注入到target的f属性中
	 */
	public static void fillWithSetter(String key, Object target, Object val, Class<?> paramClazz) {
		try {
			
			/**
			 * 根据set方法注入属性
			 */
			String setter = "set" + StringUtil.toUpperCaseFirst(key);
			Method setMethod = getSetterFromClass(target.getClass(), setter, paramClazz);
			if (setMethod == null) {System.out.println("################## 找不到对应的setter方法。。。。。。。。" + setter);}
			setMethod.invoke(target, val);
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 是否有setter方法
	 */
	public static boolean hasSetter(Class<?> c, String name) {
		String setter = "set" + StringUtil.toUpperCaseFirst(name);
		for (Method method : c.getDeclaredMethods()) {
			if (method.getName().equals(setter)) {
				return true;
			}
		}
		
		if (c.getSuperclass() == Object.class) {
			return false;
		}
		
		return hasSetter(c.getSuperclass(), name);
	}
	
	
	/**
	 * 获取setter方法， 递归查询父类是否存在该方法
	 */
	public static Method getSetterFromClass(Class<?> c, String setterName, Class<?>... params) {
		if (c == Object.class) return null;
		try {
			Method result = c.getDeclaredMethod(setterName, params);
			return result;
		} catch (NoSuchMethodException | SecurityException e) {
			return getSetterFromClass(c.getSuperclass(), setterName, params);
		}
	}
	
	
	/**
	 * 从枚举中获取该枚举的注解信息
	 */
	public static Field getFieldFromEnum(Enum<?> obj) {
		try {
			return obj.getClass().getField(obj.name());
		} catch (NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 从枚举中获取该枚举的注解信息
	 */
	public static <T extends Annotation> T getAnnotationFromEnum(Enum<?> obj, Class<T> t) {
		Field f = getFieldFromEnum(obj);
		if (f == null) return null;
		return f.getAnnotation(t);
	}
	
	/**
	 * 获取clazz类的泛型Class
	 */
	public static Class<?> getGenericClass(Class<?> clazz) {
		Type superClass = clazz.getGenericSuperclass(); 
		if (superClass == Object.class) {
			Type superInterface[] = clazz.getGenericInterfaces();
			if (superInterface != null && superInterface.length > 0) {
				superClass = clazz.getGenericInterfaces()[0];
			}
		}
		return getGenericClass(superClass);
	}
	
	/**
	 * 从属性中获取该属性的类型的泛型class
	 */
	public static Class<?> getGenericClass(Field f) {
		return getGenericClass(f.getGenericType());
	}
	
	/**
	 * 从参数化类型中取出第一个参数类型
	 */
	private static Class<?> getGenericClass(Type paramType) {
		try {
			if (paramType != null && paramType instanceof ParameterizedType) {
				Type[] params = ((ParameterizedType) paramType).getActualTypeArguments();
				if (params.length > 0) {
					return (Class<?>) params[0];
				}
			}
		} catch (Exception e) {}
		return null;
	}
	
	/**
	 * 遍历类中的属性，并获取属性上特定的注解
	 */
	public static <T extends Annotation> void forEachField(Class<?> t, Consumer<T> c, Class<T> s) {
		_forEachField(t, c, s);
	}
	
	/**
	 * 遍历枚举中属性
	 */
	public static <T extends Annotation> void forEachEnum(Class<? extends Enum<?>> t, Consumer<T> c, Class<T> s) {
		for (Enum<?> e : t.getEnumConstants()) {
			try {
				Field f = e.getClass().getField(e.name());
				c.apply(e.name(), f, f.getAnnotation(s));
			} catch (NoSuchFieldException | SecurityException e1) {
				e1.printStackTrace();
			}
		}
	}
	
	/**
	 * 遍历累属性, 递归遍历父类属性
	 */
	private static <T extends Annotation> void _forEachField(Class<?> t, Consumer<T> c, Class<T> s) {
		for (Field f : t.getDeclaredFields()) {
			f.setAccessible(true);
			if (!c.apply(f.getName(), f, f.getAnnotation(s))) 
				break;
		}
		
		if (t.getSuperclass() != Object.class) {
			_forEachField(t.getSuperclass(), c, s);
		}
	}
	
	/**
	 * 
	 */
	public static boolean isSimpleType(Class<?> c) {
		Class<?> simpleClasses[] = {
				Enum.class,
				Class.class,
				Object.class,
				String.class, 
				Integer.class,
				Date.class,
				long.class, 
				Long.class, 
				float.class,
				Double.class,
				double.class,
				BigDecimal.class,
				Short.class,
				short.class,
				int.class,
				boolean.class,
				Boolean.class};
		for (Class<?> simple : simpleClasses) {
			if (simple == c) return true;
		}
		
		Class<?> sClassed[] = {Set.class, Map.class, Enum.class};
		for (Class<?> sc : sClassed) {
			if (sc.isAssignableFrom(c)) return true;
		}
		return false;
	}
	
	/**
	 * 迭代父类获取某属性
	 */
	public static Field getField(Class<?> t, String name) {
		try {
			Field result = t.getDeclaredField(name);
			if (result != null)	return result;
		} catch (NoSuchFieldException | SecurityException e) {/*ignore*/}
		
		if (t.getSuperclass() != Object.class)
			return getField(t.getSuperclass(), name);
		
		return null;
	}
}
