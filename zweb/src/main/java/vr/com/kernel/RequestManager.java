package vr.com.kernel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import vr.com.kernel.processor.ValueProcessorUtil;
import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.HttpUtil.MyResponse;
import vr.com.kernel.request.Request;
import vr.com.util.CacheUtil;

public class RequestManager {
	
	private String url;
	
	private String clientName;
	
	private List<RequestedParam> paramArr;
	
	private MyResponse response = null;
	
	private Map<String, Object> params = new HashMap<String, Object>();
	
	private Map<String, Object> originParams = new HashMap<String, Object>();
	
	
	public RequestManager(String url, String clientName, List<RequestedParam> paramArr) {
		 this.url = url;
		 this.paramArr = paramArr;
		 this.clientName = clientName;
		 
		 doRequest();
	}
	
	public String getResponseText() {
		return response.toString();
	}
	
	public String getParamsInfo() {
		return JSONObject.toJSONString(params);
	}
	
	public String getOriginParamsInfo() {
		return JSONObject.toJSONString(originParams);
	}
	
	private void doRequest() {
		
		Client client = ClientFactory.getClient(clientName);
		
		for (RequestedParam rParam : paramArr) {
			params.put(rParam.getKey(), 
					ValueProcessorUtil.process(rParam.getValue(), rParam.getProcessorKeys()));
			originParams.put(rParam.getKey(), rParam.getValue());
		}
		
		Request req = new Request(false, url, params);
		
		response = client.httpRequest(req);
		
		// record the history of request
		recordHistory();
	}
	
	private void recordHistory() {
		/**
	 	record : {
	 		url: "",
	 		clientName: ""
	 		paramsInfo: "",
	 		
	 		params: "",
	 		result: ""
	 	}
		*/
		try {
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("url", url);
			record.put("clientName", clientName);
			record.put("paramsInfo", paramArr);
			record.put("params", originParams);
			record.put("result", JSON.parse(getResponseText()));
			CacheUtil.add(JSON.toJSONString(record));
		} catch (JSONException e) {
			System.out.println("记录失败, 响应不是JSON格式");
		} catch (Exception e) {
			System.out.println("记录失败, 持久化地址错误");
		}
	}
}
