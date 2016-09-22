package vr.com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import vr.com.data.dao.InterfaceEntiyDao;
import vr.com.kernel.InterfaceManager;
import vr.com.kernel.RequestBody;
import vr.com.kernel.RequestManager;
import vr.com.kernel.processor.ValueProcessorFactory;
import vr.com.kernel.request.ClientFactory;
import vr.com.util.CacheUtil;

@Controller
@RequestMapping("/my")
public class TestInterfaceAction {
	
	private InterfaceEntiyDao interfaceEntityDao = new InterfaceEntiyDao();
	
	@RequestMapping("/testRest")
	public String toTestRest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("clients", JSON.toJSONString(ClientFactory.keySet()));
		request.setAttribute("processors", JSON.toJSONString(ValueProcessorFactory.keySet()));
		request.setAttribute("cache", CacheUtil.toJson());
		return "zTestRest";
	}
	
	@ResponseBody
	@RequestMapping(value="/send", produces = "text/html;charset=utf-8")
	public Object send(HttpServletRequest request, HttpServletResponse response,
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
		RequestBody requestBody = new RequestBody(url, clientName, paramsInfo);
		
		// 根据url或参数信息中包含的"描述信息"，来存储接口相关信息 
		InterfaceManager.process(requestBody);
		
		// 托管请求管理者，在构建过程中，已经发送请求。
		RequestManager manager = new RequestManager(requestBody);
		
		// 保存参数与响应结果
		JSONObject result = new JSONObject();
		result.put("params", manager.getParamsInfo());
		result.put("result", manager.getResponseText());
		return result;
	}
	
	@ResponseBody
	@RequestMapping(value="/interface/query", produces = "text/html;charset=utf-8")
	public Object query(HttpServletRequest request, HttpServletResponse response,
			String keyword) {
		return interfaceEntityDao.query(keyword);
	}
}