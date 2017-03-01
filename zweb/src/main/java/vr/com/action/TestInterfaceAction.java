package vr.com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import vr.com.data.springData.repository.InterfaceEntityRepository;
import vr.com.data.springData.repository.TestCaseRepository;
import vr.com.kernel.InterfaceManager;
import vr.com.kernel.RequestBody;
import vr.com.kernel.RequestManager;
import vr.com.kernel.processor.ValueProcessorFactory;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel2.Permanent;
import vr.com.kernel2.testCase.TestCase;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.TestCaseEntity;

@Controller
@RequestMapping("/my")
public class TestInterfaceAction {
	
	// private InterfaceEntiyDao interfaceEntityDao = new InterfaceEntiyDao();
	@Autowired
	private InterfaceEntityRepository interfaceEntityDao;
	
	@Autowired
	private TestCaseRepository testCaseRepository;
	
	
	@RequestMapping("/testRest")
	public String toTestRest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("clients", JSON.toJSONString(ClientFactory.keySet()));
		request.setAttribute("processors", JSON.toJSONString(ValueProcessorFactory.keySet()));
		request.setAttribute("cache", "{}");
		return "zTestRest";
	}
	
	@ResponseBody
	@RequestMapping(value="/send")
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
		InterfaceManager interfaceManager = new InterfaceManager();
		interfaceManager.process(requestBody);
		
		// 托管请求管理者，在构建过程中，已经发送请求。
		RequestManager requestManager = new RequestManager(requestBody);
		
		// 搜集请求结果
		interfaceManager.addResult(requestManager.getResponseText());
		
		// 保存参数与响应结果
		JSONObject result = new JSONObject();
		result.put("params", requestManager.getParamsInfo());
		result.put("result", requestManager.getResponseText());
		return result;
	}
	
	@ResponseBody
	@RequestMapping("/interface/query")
	public Object query(HttpServletRequest request, HttpServletResponse response,
			String keyword) {
		return interfaceEntityDao.query(keyword);
	}
	
	@ResponseBody
	@RequestMapping("/interface/update")
	public Object update(HttpServletRequest request, HttpServletResponse response,
			String entityStr) {
		InterfaceEntity entity = JSONObject.parseObject(entityStr, InterfaceEntity.class);
		interfaceEntityDao.updateByUrl(entity.getUrl(), entity);
		return "";
	}
	
	@ResponseBody
	@RequestMapping("/testCase/query")
	public Object testQuery(HttpServletRequest request, HttpServletResponse response,
			String keyword) {
		return JSONObject.toJSONString(testCaseRepository.findAll());
	}
	
	@ResponseBody
	@RequestMapping("/testCase/update")
	public Object query(HttpServletRequest request, HttpServletResponse response,
			String id,
			String name,
			String exp) {
		TestCaseEntity entity = new TestCaseEntity();
		entity.setId(id);
		entity.setName(name == null ? "地球村民" : name);
		entity.setExpression(exp);
		testCaseRepository.save(entity);
		return "success";
	}
	
	@ResponseBody
	@RequestMapping("/interface/{id}")
	public Object queryByID(HttpServletRequest request, HttpServletResponse response,
			@PathVariable String id) {
		return interfaceEntityDao.findOne(id);
	}
	
	
	@ResponseBody
	@RequestMapping("/testCase/execute")
	public Object doExec(HttpServletRequest request, HttpServletResponse response,
			String id,
			String name,
			String host,
			String client) {
		
		// 更新测试用例数据
		TestCaseEntity entity = testCaseRepository.findOne(id);
		entity.setName(name);
		entity.setHost(host);
		entity.setClient(client);
		testCaseRepository.save(entity);
		
		// 执行
		TestCase testCase = Permanent.cloneFrom(entity, TestCase.class);
		return testCase.invoke().getAll();
	}
}