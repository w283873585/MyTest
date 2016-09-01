package vr.com.pojo;

import java.util.List;

public class VRInterface {
	
	private String url;
	private String desc;
	private List<VRParam> params;
	
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
	public List<VRParam> getParams() {
		return params;
	}
	public void setParams(List<VRParam> params) {
		this.params = params;
	}
}
