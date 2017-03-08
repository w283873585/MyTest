package vr.com.pojo.cm;

import org.springframework.data.annotation.Id;

public class MyEvent extends Created{
	@Id
	private String id;
	
	/**
	 * 2017-03-08
	 */
	private String date;
	
	private String desc;
	
	private Integer level;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}
}
