package jun.learn.scene.res;

import java.io.Serializable;


/**
 * 返回码基础对象
 * 
 * @author 易茂剑
 * 
 */
public class BaseResInfo<T extends ResInfo> implements Serializable {

	private static final long serialVersionUID = 730550958919883038L;
	private String resCode; // 返回码
	private String resDesc; // 返回描述

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResDesc() {
		return resDesc;
	}

	public void setResDesc(String resDesc) {
		this.resDesc = resDesc;
	}
	
	public BaseResInfo<T> error(T res) {
		this.setResCode(res.getResCode());
		this.setResDesc(res.getResDesc());
		return this;
	}
	
	public BaseResInfo<T> error(Common res) {
		this.setResCode(res.getResCode());
		this.setResDesc(res.getResDesc());
		return this;
	}
}
