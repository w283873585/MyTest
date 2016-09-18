package vr.com.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;

import vr.com.data.dao.InterfaceEntiyDao;
import vr.com.kernel.processor.ValueProcessorFactory;
import vr.com.kernel.processor.ValueProcessorUtil;
import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.Request;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;
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
		
		JSONObject result = new JSONObject();
		/**
		paramsInfo : [{
			key: ""
			value: ""
			processorKeys: "a,b,c"
		}]
		*/
		JSONArray paramArr = JSONArray.parseArray(paramsInfo);
		url = interfacePersist(url, paramArr);
		
		//  获取加工后的参数信息
		Map<String, Object> params = new HashMap<String, Object>();
		for (int i = 0; i < paramArr.size(); i++) {
			JSONObject obj = paramArr.getJSONObject(i);
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
		try {
			Map<String, Object> record = new HashMap<String, Object>();
			record.put("url", url);
			record.put("clientName", clientName);
			record.put("paramsInfo", paramArr);
			record.put("params", params);
			record.put("result", JSON.parse(responseText));
			CacheUtil.add(JSON.toJSONString(record));
		} catch (JSONException e) {
			System.out.println("记录失败, 响应不是JSON格式");
		} catch (Exception e) {
			System.out.println("记录失败, 持久化地址错误");
		}
		return result;
	}
	
	// 返回处理过的url
	private String interfacePersist(String url, JSONArray paramArr) {
		
		/*
		 *	接口必须有name, 
		 *	有name则作为接口实体, 存储在mongodb
		 *	
		 *	TODO 增加结果描述 
		 *	<code>
		 *		manager.process(url, paramArr);
		 *		manager.addResult(obj);
		 *		manager.exec();
		 *	</code>
		 */
		url = url.trim();
		String separator = "->";
		String expression[] = url.split(separator);
		if (expression.length > 1) {
			InterfaceEntity entity = new InterfaceEntity();
			
			url = expression[0];
			String interfaceUrl = url.replaceFirst(".*:[0-9]*", ""); 
			entity.setUrl(interfaceUrl);
			entity.setName(expression[1]);
			
			if (expression.length > 2) entity.setDesc(expression[2]);
			
			List<InterfaceParam> iParams = new ArrayList<InterfaceParam>();
			for (int i = 0; i < paramArr.size(); i++) {
				JSONObject obj = paramArr.getJSONObject(i);
				InterfaceParam param = new InterfaceParam();
				String keyExpressions[] = obj.getString("key").trim()
						.split(separator);
				param.setKey(keyExpressions[0]);
				param.setConstraint(obj.getString("processorKeys"));
				if (keyExpressions.length > 1) {
					obj.put("key", keyExpressions[0]);
					param.setDesc(keyExpressions[1]);
				}
				iParams.add(param);
			}
			entity.setParams(iParams);
			entity.setResults(new ArrayList<InterfaceParam>());
			
			// 持久化数据
			if (!interfaceEntityDao.existInterface(interfaceUrl))
				interfaceEntityDao.insert(entity);
			else
				interfaceEntityDao.updateByUrl(interfaceUrl, entity);
		}
		
		return url;
	}
	
	@ResponseBody
	@RequestMapping(value="/interface/query", produces = "text/html;charset=utf-8")
	public Object query(HttpServletRequest request, HttpServletResponse response,
			String keyword) {
		return interfaceEntityDao.query(keyword);
	}
}