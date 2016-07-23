package learn.client.check;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Iterator;

import learn.client.check.VR_ConstraintEnum.Result;




public class VR_CheckUtil {
	
	public static boolean check(Object o, BaseResCode resp) {
		try {
			RestrictFieldIter iter = new RestrictFieldIter(o.getClass());
			for (RestrictField rf : iter) {
				Result result = rf.getResult(o);
				if (!result.isSuccess()) {
					resp.setResCode(ResCodeConstans.parmsIsNull);
					resp.setResDesc("[" + rf.getName() + "]" + result.getResult().toString());
					return false;
				}
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		resp.setResCode(ResCodeConstans.serverError);
		resp.setResDesc("参数验证错误");
		return false;
	}
	
	static class RestrictFieldIter implements Iterable<RestrictField>{

		public RestrictFieldIter(Class<?> clazz) {
			this.fArr = clazz.getDeclaredFields();
		}
		
		private Field fArr[];
		
		@Override
		public Iterator<RestrictField> iterator() {
			return new Iterator<VR_CheckUtil.RestrictField>() {
				private int fIndex = 0;
				private RestrictField cur = _next();
				
				private RestrictField _next() {
					while (fIndex < fArr.length ) {
						VR_Constraint v_c = fArr[fIndex++].getAnnotation(VR_Constraint.class);
						if (v_c != null) {
							return new RestrictField(fArr[fIndex - 1], v_c);
						}
					}
					return null;
				}
				
				@Override
				public void remove() {
					throw new RuntimeException("no support remove");
				}
				
				@Override
				public RestrictField next() {
					if (!hasNext()) {
						throw new RuntimeException("no data anymore");
					}
					RestrictField result = cur;
					cur = _next();
					return result;
				}

				public boolean hasNext() {
					return cur != null;
				}
			};
		}
	}
	
	static class RestrictField{
		Field f;
		VR_Constraint c;
		
		public RestrictField(Field f, VR_Constraint c) {
			this.f = f;
			this.c = c;
		}
		
		public String getName() {
			return f.getName();
		}
		
		public Result getResult(Object o) throws Exception {
			Result result = null;
			f.setAccessible(true);
			for (VR_ConstraintEnum e : c.value()) {
				result = e.check(f.get(o));
				if (!result.isSuccess()) {
					return result;
				} else if (e == VR_ConstraintEnum.Rsa) {
					setVal(o, result.getResult());
				}
			}
			return result;
		}
		
		private void setVal(Object o, Object value) throws Exception{
			Class<?> c = o.getClass();
			String fieldName = f.getName();
			String setMethodName = "set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1); 
	        Method setMethod;
			setMethod = c.getMethod(setMethodName, f.getType());
			setMethod.invoke(o, value); 
		}
	}
}
