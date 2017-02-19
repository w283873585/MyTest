package vr.com.kernel2;

import com.alibaba.fastjson.JSONObject;

import vr.com.kernel2.testCase.TestCase;

public class Client {
	public static void main(String[] args) {
		
		TestCase testCase = new TestCase();
		testCase.setExpression("1563464898 1,2,3");
		System.out.println(JSONObject.toJSONString(testCase.invoke()));
	}
}
