package vr.com.data.springData.pojo;

import java.util.List;

import org.springframework.data.annotation.Id;

public class InterfaceEntity {
	@Id
	private String id;
	
	private String url;
	private String name;
	private String desc = "";
	private List<InterfaceParam> params;
	private List<InterfaceParam> results;
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
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
	public List<InterfaceParam> getResults() {
		return results;
	}
	public void setResults(List<InterfaceParam> results) {
		this.results = results;
	}
}
