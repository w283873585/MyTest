package vr.com.kernel2.chain;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.alibaba.fastjson.JSONObject;

import vr.com.data.springData.pojo.TestCaseEntity;
import vr.com.data.springData.repository.InterfaceEntityRepository;
import vr.com.data.springData.repository.TestCaseRepository;
import vr.com.kernel.processor.ValueProcessorUtil;
import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.Request;
import vr.com.kernel2.Permanent;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;

public class TestCase implements Permanent<TestCaseEntity>{

	private String expression = null;
	private Imagine imagine = new Imagine(new TestCaseResolver());
	
	@Override
	public TestCaseEntity toPojo() {
		TestCaseEntity entity = new TestCaseEntity();
		entity.setExpression(expression);
		return entity;
	}

	@Override
	public Permanent<TestCaseEntity> parse(TestCaseEntity t) {
		TestCase testCase = new TestCase();
		setExpression(t.getExpression());
		return testCase;
	}

	@Override
	public TestCaseRepository getRepository() {
		return getSpringBean(TestCaseRepository.class);
	}
	
	public void setExpression(String exp) {
		this.expression = exp;
		for (String subExp : exp.split("\n"))
			imagine.addNode(subExp);
	}
	
	public void invoke() {
		imagine.invoke();
	}
	
	private static <T> T getSpringBean(Class<T> clazz) {
		WebApplicationContext wac = ContextLoader.getCurrentWebApplicationContext();
		return wac.getBean(clazz);
	}
	
	private static class TestCaseResolver implements Resolver{

		@Override
		public void resolve(String expression, Context ctx) {
			
			String[] exps = expression.split("\\s+");
			String interfaceId = exps[0];
			String parameters = exps[1];
			String expect = exps[2];
			
			JSONObject result = doRequest(interfaceId, parameters, ctx);
			if (!getExpecter(expect).match(result)) {
				ctx.commit("result no matched");
				return;
			}
			
			for (Entry<String, Object> entity : result.entrySet())
				ctx.put(entity.getKey(), entity.getValue());
		}
		
		/**
		 * 获取对应的期望着
		 */
		private Expecter getExpecter(final String expect) {
			return new Expecter() {
				
				@Override
				public boolean match(JSONObject result) {
					return true;
				}
			};
		}
		
		/**
		 * 根据接口, 发送请求, 获取结果
		 *  //TODO 请求返回的结果
		 */
		public JSONObject doRequest(String interfaceId, String param, Context ctx) {
			InterfaceEntityRepository interfaceEntityDao = getSpringBean(InterfaceEntityRepository.class);
			InterfaceEntity entity = interfaceEntityDao.findOne(interfaceId);
			
			Client client = ClientFactory.getClient("vrsoft");
			SplitHelper util = new SplitHelper(param);
			Map<String, Object> paramsMap = new HashMap<String, Object>();
			for (InterfaceParam iParam : entity.getParams())
				paramsMap.put(iParam.getKey(), ValueProcessorUtil
						.process(extract(util.next(), ctx), iParam.getConstraint()));
			
			Request req = new Request(false, entity.getUrl(), paramsMap);
			return JSONObject.parseObject(client.httpRequest(req).toString());
		}
		
		/**
		 * 表达式中的参数可能为引用(context的key值), 
		 * 也可能为真实值
		 */
		private String extract(String key, Context ctx) {
			if (key.startsWith("{") && key.endsWith("}"))
				return ctx.get(key.substring(1, key.length() - 1)).toString();
			return key;
		}
	}
	
	private static class SplitHelper{
		private static final String separator = ",";
		private int index;
		private String body[];
		
		public SplitHelper(String origin) {
			origin = origin.trim();
			this.body = origin.split(separator);
		}
		
		public String next() {
			if (index < body.length) {
				return body[index++];
			}
			return "";
		}
	}
}
