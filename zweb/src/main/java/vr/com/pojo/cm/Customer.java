package vr.com.pojo.cm;

import org.springframework.data.annotation.Id;

public class Customer extends Created{
	
	@Id
	private String id;
	
	private String name;
	
	private Integer sex;
	
	private Integer age;
	
	private String mobile;
	
	/**
	 * 1990-08-08
	 */
	private String borthYear;
	
	/**
	 * 10-18
	 */
	private String birthday;
	
	/**
	 * 来自哪里
	 */
	private String from;
	
	/**
	 * 居住地
	 */
	private String residence;
	
	/**
	 * 描述 
	 */
	private String desc;

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

	public Integer getSex() {
		return sex;
	}

	public void setSex(Integer sex) {
		this.sex = sex;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getBorthYear() {
		return borthYear;
	}

	public void setBorthYear(String borthYear) {
		this.borthYear = borthYear;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getResidence() {
		return residence;
	}

	public void setResidence(String residence) {
		this.residence = residence;
	}
}
