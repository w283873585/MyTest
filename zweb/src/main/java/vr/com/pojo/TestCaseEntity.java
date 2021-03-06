package vr.com.pojo;

import org.springframework.data.annotation.Id;

public class TestCaseEntity {
	@Id
	private String id;
	
	private String name;
	
	private String host;
	
	private String client;
	
	private String globalExp;
	
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getClient() {
		return client;
	}

	public void setClient(String client) {
		this.client = client;
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getGlobalExp() {
		return globalExp;
	}

	public void setGlobalExp(String globalExp) {
		this.globalExp = globalExp;
	}
}
