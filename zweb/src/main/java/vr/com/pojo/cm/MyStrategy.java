package vr.com.pojo.cm;

import java.util.List;

import org.springframework.data.annotation.Id;

public class MyStrategy extends Created{
	@Id
	private String id;
	
	private String name;
	
	private List<MyStrategyDetail> details;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<MyStrategyDetail> getDetails() {
		return details;
	}

	public void setDetails(List<MyStrategyDetail> details) {
		this.details = details;
	}
}
