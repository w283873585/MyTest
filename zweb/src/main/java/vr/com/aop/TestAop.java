package vr.com.aop;

import org.springframework.stereotype.Service;

@Service
public class TestAop implements TestAopI{
	public void doSomething() {
		System.out.println("我能做任何事");
	}
}
