package vr.com.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class LogService {
	
	@Pointcut("execution(* *())")
	private void log() {}
	
	@Before("execution(* *())")
	public void beforeLog() {
		System.out.println("天下之大");
	} 
}
