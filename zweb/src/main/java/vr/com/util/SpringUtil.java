package vr.com.util;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

public class SpringUtil {
	public static <T> T getSpringBean(Class<T> clazz) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(clazz);
	}
}
