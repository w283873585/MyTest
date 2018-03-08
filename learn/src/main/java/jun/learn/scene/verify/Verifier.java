package jun.learn.scene.verify;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import jun.learn.scene.verify.ReflectUtil.Consumer;

public class Verifier {
	private ConstraintResolver resolver = new StandardConstraintResolver();
	
	public boolean verify(final HttpServletRequest request, Class<?> targetClass) {
		RequiredVerifyData data = new RequiredVerifyData() {
			@Override
			public Object get(String key) {
				return request.getParameter(key);
			}
		};
		return resolver.resolveConstraint(targetClass).matches(data);
	}
	
	public interface ConstraintResolver{
		public Constraint resolveConstraint(Class<?> targetClass);
	}
	
	public interface RequiredVerifyData{
		public Object get(String key);
	}
	
	public interface Constraint{
		boolean matches(RequiredVerifyData data);
	}
	
	private class StandardConstraint implements Constraint {
		private Map<String, Desc> descMap = new HashMap<String, Desc>();
		
		void put(String key, Desc value) {
			this.descMap.put(key, value);
		}
		
		@Override
		public boolean matches(RequiredVerifyData data) {
			for (Map.Entry<String, Desc> entry : descMap.entrySet()) {
				Object value = data.get(entry.getKey());
				Desc desc = entry.getValue();
				if (desc.optional() == true && value == null) {
					return true;
				}
				
				String regex = desc.regex();
				if (notEmpty(regex) ? notMatch(regex, value) 
						: !desc.limit().matches(value)) {
					return false;
				}
			}
			return true;
		}
		
		private boolean notEmpty(String str) {
			return str == null || "".equals(str);
		}
		
		private boolean notMatch(String regex, Object value) {
			if (value == null) return true;
			return !Pattern.matches(regex, value.toString());
		}
	}
	
	private class StandardConstraintResolver implements ConstraintResolver{
		private ConcurrentMap<Class<?>, Constraint> cache = new ConcurrentHashMap<Class<?>, Constraint>();
		
		@Override
		public Constraint resolveConstraint(Class<?> targetClass) {
			/**
			Constraint cachedConstraint = cache.get(targetClass);
			if (cachedConstraint != null) {
				return cachedConstraint;
			}
			
			final StandardConstraint constraint = new StandardConstraint();
			ReflectUtil.forEachField(targetClass, new Consumer<Desc>() {
				@Override
				public boolean apply(String name, Field f, Desc desc) {
					constraint.put(name, desc);
					return true;
				}
			}, Desc.class);
			cache.put(targetClass, constraint);
			return constraint;
			*/
			
			final StandardConstraint constraint = new StandardConstraint();
			Constraint cachedConstraint = cache.putIfAbsent(targetClass, constraint);
			if (cachedConstraint != null) {
				return cachedConstraint;
			}
			ReflectUtil.forEachField(targetClass, new Consumer<Desc>() {
				@Override
				public boolean apply(String name, Field f, Desc desc) {
					constraint.put(name, desc);
					return true;
				}
			}, Desc.class);
			return constraint;
		}
	}
}
