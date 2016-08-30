package vr.com.restManager;

import java.util.List;

import vr.com.request.Client;

public class InterfaceEntity {
	private String url;
	
	private String name;
	
	private Client client;
	
	private List<InterfaceParam> params;

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

	public Client getClient() {
		return client;
	}

	public void setClient(Client client) {
		this.client = client;
	}

	public List<InterfaceParam> getParams() {
		return params;
	}

	public void setParams(List<InterfaceParam> params) {
		this.params = params;
	}
}
