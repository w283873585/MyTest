package vr.com.pojo.cm;

import org.springframework.data.annotation.Id;

public class CustomerRecord extends Created{
	@Id
	private String id;
	
	private String customerId;
	
	private String desc;
	
	/**
	 * 消费金额， 单位分
	 */
	private Integer consume;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Integer getConsume() {
		return consume;
	}

	public void setConsume(Integer consume) {
		this.consume = consume;
	}
}
