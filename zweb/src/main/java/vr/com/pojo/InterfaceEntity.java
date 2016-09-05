package vr.com.pojo;

import java.util.List;

@Pojo
public class InterfaceEntity {
	
	private String url;
	private String desc;
	private List<InterfaceParam> params;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	public List<InterfaceParam> getParams() {
		return params;
	}
	public void setParams(List<InterfaceParam> params) {
		this.params = params;
	}
}
