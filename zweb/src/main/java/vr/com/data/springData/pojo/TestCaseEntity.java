package vr.com.data.springData.pojo;

import org.springframework.data.annotation.Id;

public class TestCaseEntity {
	@Id
	private String id;
	
	private String expression;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExpression() {
		return expression;
	}

	public void setExpression(String expression) {
		this.expression = expression;
	}
}