package vr.com.action;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import vr.com.request.Client;
import vr.com.request.ClientFactory;
import vr.com.request.Request;
import vr.com.rest.ValueProcessorUtil;

@Controller
@RequestMapping("/my")
public class TestInterfaceAction {
	
	@RequestMapping("/testRest")
	public String toTestRest(HttpServletRequest request, HttpServletResponse response) {
		// request.setAttribute("params", ClientUtil.getParamInfo());
		return "zTestRest";
	}
	

	@RequestMapping(value="/send",  produces = "text/html;charset=utf-8")
	@ResponseBody
	public String send(HttpServletRequest request, HttpServletResponse response,
			String clientName,
			String url, 
			String paramsInfo) {
		/**
		paramsInfo : [{
			key: ""
			value: ""
			processorKeys: "a,b,c"
		}]
		*/
		JSONObject result = new JSONObject();
		
		// TODO record the history of request
		/**
		 	{
		 		url: "",
		 		requestProcessor: "",
		 		paramsInfo: "",
		 		
		 		params: "",
		 		result: ""
		 	}
		 */
		
		// TODO 加工请求
		Client client = ClientFactory.getClient(clientName);
		
		//  获取加工后的参数信息
		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray arr = JSONArray.parseArray(paramsInfo);
		for (int i = 0; i < arr.size(); i++) {
			JSONObject obj = arr.getJSONObject(i);
			params.put(obj.getString("key"), 
					ValueProcessorUtil.process(obj.getString("value"), obj.getString("processorKeys")));
		}
		
		// TODO 发送最终请求, 获取响应结构
		Request req = new Request(false, url, params);
		String responseText = client.httpRequest(req);
		
		result.put("params", params);
		result.put("result", responseText);
		
		return result.toJSONString();
	}
}