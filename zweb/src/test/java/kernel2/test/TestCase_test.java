package kernel2.test;

import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import vr.com.kernel2.testCase.TestCase;

public class TestCase_test {
	TestCase test = new TestCase();
	
	@Test
	public void testInvoke() {
		test.setExpression("580ec693bcb7a866205d897d 1,2a9bdb9a2133d50c");
		test.invoke();
	}
	
	public static void main(String[] args) {
      Result result = JUnitCore.runClasses(TestCase_test.class);
      for (Failure failure : result.getFailures()) {
         System.out.println(failure.toString());
      }
      System.out.println(result.wasSuccessful());
   }
}
