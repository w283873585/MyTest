package jun.learn.foundation.annotations;

import java.lang.reflect.Method;
import java.util.*;

public class UseCaseTracker {
	public static void trackUseCases(List<Integer> useCases, Class<?> cl) {
		for (Method m : cl.getDeclaredMethods()) {
			UserCase uc = m.getAnnotation(UserCase.class);
			if (uc != null) {
				System.out.println("Found Use Case :" + uc.id() + " " + uc.description());
				useCases.remove(new Integer(uc.id()));
			}
		}
		
		for (int i : useCases) {
			System.out.println("Warning : missing use case-" + i);
		}
	}
	
	public static void testVariableParam(int ...params) {
		System.out.println(params instanceof int[]); //true
		System.out.println(params.length);
	}
	
	public static void main(String[] args) {
		List<Integer> useCases = new ArrayList<Integer>();
		Collections.addAll(useCases, 47, 48, 49, 50);
		trackUseCases(useCases, PassWordUtils.class);
		
		testVariableParam(1, 2, 3);
	}
}
