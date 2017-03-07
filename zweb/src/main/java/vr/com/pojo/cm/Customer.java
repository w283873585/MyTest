package vr.com.pojo.cm;

import org.springframework.data.annotation.Id;

public class Customer {
	
	@Id
	private String id;
	
	private String name;
	
	private Integer sex;
	
	private Integer age;
	
	private String mobile;
	
	private String desc;
}
