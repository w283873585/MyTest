package vr.com.kernel2.testCase;

import java.util.function.BiConsumer;

import vr.com.data.springData.repository.InterfaceEntityRepository;
import vr.com.data.springData.repository.TestCaseRepository;
import vr.com.kernel2.Permanent;
import vr.com.kernel2.httpAPI.HttpAPI;
import vr.com.kernel2.httpAPI.HttpAPIResult;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.TestCaseEntity;
import vr.com.util.SpringUtil;
import vr.com.util.text.SplitUtil;
import vr.com.util.text.SplitUtil.FragProvider;

public class TestCase implements Permanent<TestCaseEntity>{

	private String host;
	private String client;
	
	private String expression = null;
	private Imagine imagine = new Imagine(new TestCaseResolver());
	
	public void setExpression(String exp) {
		this.expression = exp;
		for (String subExp : exp.split("\n"))
			imagine.addNode(subExp);
	}
	
	public Context invoke() {
		return imagine.invoke();
	}
	
	/**
	 * the resolver for testcase expression
	 */
	private class TestCaseResolver implements Resolver{

		@Override
		public void resolve(String expression, Context ctx) {
			
			FragProvider provider = SplitUtil.split(expression, "\\s+");
			String interfaceId = provider.get();
			String parameters = provider.get();
			String expect = provider.get();
			
			HttpAPIResult result = doRequest(interfaceId, parameters, ctx);
			if (!getExpecter(expect).match(result)) {
				ctx.commit("result no matched");
				return;
			}
			
			result.foreach(new BiConsumer<String, Object>() {
				@Override
				public void accept(String key, Object value) {
					ctx.put(key, value);
				}
			});
		}
		
		/**
		 * 获取对应的期望着
		 */
		private Expecter getExpecter(final String expect) {
			return new Expecter() {
				@Override
				public boolean match(HttpAPIResult result) {
					return true;
				}
			};
		}
		
		/**
		 * 根据接口, 发送请求, 获取结果
		 */
		public HttpAPIResult doRequest(String interfaceId, String param, Context ctx) {
			InterfaceEntityRepository interfaceEntityDao = SpringUtil.getSpringBean(InterfaceEntityRepository.class);
			InterfaceEntity entity = interfaceEntityDao.findOne(interfaceId);
			
			HttpAPI api = Permanent.cloneFrom(entity, HttpAPI.class);
			api.setClient(client);
			api.setHost(host);
			return api.execute(format(param, ctx));
		}
		
		/**
		 * 表达式中的参数可能为引用(context的key值), 
		 * 也可能为真实值
		 */
		private String[] format(String param, Context ctx) {
			String[] origin = param.split(",");
			String[] result = new String[origin.length];
			for (int i = 0; i < origin.length; i++) {
				String key = origin[i];
				if (key.startsWith("{") && key.endsWith("}"))
					result[i] = ctx.get(key.substring(1, key.length() - 1)).toString();
				else 
					result[i] = key;
			}
			return result;
		}
	}
	
	
	/**
	 *  the implement for Permanent
	 */
	@Override
	public TestCaseEntity toPojo() {
		TestCaseEntity entity = new TestCaseEntity();
		entity.setExpression(expression);
		return entity;
	}

	@Override
	public Permanent<TestCaseEntity> cloneFrom(TestCaseEntity t) {
		setExpression(t.getExpression());
		this.client = t.getClient();
		this.host = t.getHost();
		return this;
	}

	@Override
	public TestCaseRepository getRepository() {
		return SpringUtil.getSpringBean(TestCaseRepository.class);
	}
}
