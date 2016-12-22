package vr.com.aop;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class LogService {
	
	@Pointcut("execution(* *(..))")
	private void log() {}
	
	@Before("log()")
	public void beforeLog() {
		System.out.println("天下之大");
	}
}
