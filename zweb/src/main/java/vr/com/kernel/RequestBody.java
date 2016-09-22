package vr.com.kernel;

import java.util.List;

import com.alibaba.fastjson.JSONArray;

public class RequestBody {
	private String url;
	private String clientName;
	private List<RequestBodyParam> params;
	
	public RequestBody(String url, String clientName, String paramsInfo) {
		this.url = url;
		this.clientName = clientName;
		this.params = JSONArray.parseArray(paramsInfo, RequestBodyParam.class);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getClientName() {
		return clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public List<RequestBodyParam> getParams() {
		return params;
	}

	public void setParams(List<RequestBodyParam> params) {
		this.params = params;
	}
}
