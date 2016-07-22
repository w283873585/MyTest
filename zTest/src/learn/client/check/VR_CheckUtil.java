package learn.client.check;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import learn.client.check.VR_ConstraintEnum.Result;



public class VR_CheckUtil {
	
	public static boolean check(Object o, BaseResCode resp) {
		Class<?> clazz = o.getClass();
		try {
			Field fArr[] = clazz.getDeclaredFields();
			for (Field f : fArr) {
				f.setAccessible(true);
				Annotation[] aArr = f.getAnnotations();
				for (Annotation a : aArr) {
					if (a instanceof VR_Constraint) {
						VR_Constraint constraint = (VR_Constraint) a;
						VR_ConstraintEnum[] eArr = constraint.value();
						String infos[] = constraint.info();
						for (int i = 0; i < eArr.length; i++) {
							VR_ConstraintEnum e = eArr[i];
							Result result = e.check(f.get(o));
							if (!result.isSuccess()) {
								resp.setResCode(e == VR_ConstraintEnum.Rsa ? ResCodeConstans.rsaError : ResCodeConstans.parmsIsNull);
								resp.setResDesc(infos[i]);
								return false;
							} else if (e == VR_ConstraintEnum.Rsa) {
								setVal(o, f, result.getResult());
							}
						}
					}
				}
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return true;
	}
	
	public static void setVal(Object o, Field f, Object value) {
		Class<?> c = o.getClass();
		String fieldName = f.getName();
		String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
        Method setMethod;
		try {
			setMethod = c.getMethod(setMethodName, f.getType());
			setMethod.invoke(o, value); 
		} catch (NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
