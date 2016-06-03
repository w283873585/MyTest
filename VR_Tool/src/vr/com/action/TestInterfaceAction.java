package vr.com.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import vr.com.processor.ValueProcessorFactory;
import vr.com.processor.ValueProcessorUtil;
import vr.com.request.Client;
import vr.com.request.ClientFactory;
import vr.com.request.Request;
import vr.com.util.CacheUtil;

@Controller
@RequestMapping("/my")
public class TestInterfaceAction {
	
	@RequestMapping("/testRest")
	public String toTestRest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("clients", JSON.toJSONString(ClientFactory.keySet()));
		request.setAttribute("processors", JSON.toJSONString(ValueProcessorFactory.keySet()));
		return "zTestRest";
	}
	

	@RequestMapping(value="/send", produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object send(HttpServletRequest request, HttpServletResponse response,
			String clientName,
			String url, 
			String paramsInfo) {
		
		JSONObject result = new JSONObject();
		/**
		paramsInfo : [{
			key: ""
			value: ""
			processorKeys: "a,b,c"
		}]
		*/
		//  获取加工后的参数信息
		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray arr = JSONArray.parseArray(paramsInfo);
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			params.put(obj.getString("key"), 
					ValueProcessorUtil.process(obj.getString("value"), obj.getString("processorKeys")));
		}
		
		// 获取客户端
		Client client = ClientFactory.getClient(clientName);
		
		// 发送最终请求, 获取响应结构
		Request req = new Request(false, url, params);
		String responseText = client.httpRequest(req);
		
		// 保存参数与响应结果
		result.put("params", JSON.toJSONString(params));
		result.put("result", responseText);
		
		/**
	 	record : {
	 		url: "",
	 		clientName: ""
	 		paramsInfo: "",
	 		
	 		params: "",
	 		result: ""
	 	}
		*/
		// record the history of request
		Map<String, Object> record = new HashMap<String, Object>();
		record.put("url", url);
		record.put("clientName", clientName);
		record.put("paramsInfo", paramsInfo);
		record.put("params", params);
		record.put("result", JSON.parse(responseText));
		CacheUtil.add(JSON.toJSONString(record));
		
		return result;
	}
}