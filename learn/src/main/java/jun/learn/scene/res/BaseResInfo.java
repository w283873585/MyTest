package jun.learn.scene.res;

import java.io.Serializable;


/**
 * 返回码基础对象
 * 
 * @author 易茂剑
 * 
 */
public class BaseResInfo<T extends Enum<T>> implements Serializable {

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
	
	public BaseResInfo<T> success() {
		return resCommon(Common.success);
	}
	
	public BaseResInfo<T> res(T info) {
		return extract(info);
	}
	
	public BaseResInfo<T> resCommon(Common info) {
		return extract(info);
	}
	
	private BaseResInfo<T> extract(Object o) {
		ResInfo r = o.getClass().getAnnotation(ResInfo.class);
		this.setResCode(r.resCode());
		this.setResDesc(r.resDesc());
		return this;
	}
}
