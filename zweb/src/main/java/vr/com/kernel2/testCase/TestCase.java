package vr.com.kernel2.testCase;

import java.util.function.BiConsumer;

import vr.com.data.springData.repository.InterfaceEntityRepository;
import vr.com.data.springData.repository.TestCaseRepository;
import vr.com.kernel2.Permanent;
import vr.com.kernel2.httpAPI.HttpAPI;
import vr.com.kernel2.httpAPI.HttpAPIResult;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;
import vr.com.pojo.TestCaseEntity;
import vr.com.util.ExceptionUtil;
import vr.com.util.SpringUtil;
import vr.com.util.text.SplitUtil;
import vr.com.util.text.SplitUtil.FragProvider;

public class TestCase implements Permanent<TestCaseEntity>{

	private String host;
	private String client;
	private String globalExp;
	
	private String expression = null;
	private Imagine imagine = new Imagine(new TestCaseResolver());
	
	public void setExpression(String exp) {
		this.expression = exp;
		for (String subExp : exp.split("\n"))
			imagine.addNode(subExp);
	}
	
	public Context invoke() {
		imagine.setGlobalExp(globalExp);
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
			result.foreach(new BiConsumer<String, Object>() {
				@Override
				public void accept(String key, Object value) {
					ctx.put(key, value);
				}
			});
			
			// 验证是否通过期望
			if (!getExpecter(expect).match(result)) {
				ctx.commit("result no matched!");
			}
		}
		
		/**
		 * 获取对应的期望着
		 */
		private Expecter getExpecter(final String expect) {
			return new Expecter() {
				/**
				 * a=1&b!=3
				 */
				@Override
				public boolean match(HttpAPIResult result) {
					
					class Node{
						public Node next;
						private final String key;
						private final String value;
						private final boolean equal;
						private final int isAnd;
						
						public Node(String exp) {
							int target = exp.indexOf("!=");
							this.equal = target == -1;
							if (this.equal) {
								target = exp.indexOf("=");
							}
							this.key = exp.substring(0, target);
							String lastStr = exp.substring(exp.length() - 1);
							if ("&".equals(lastStr)) {
								this.isAnd = 0;
							} else if ("|".equals(lastStr)) {
								this.isAnd = 1;
							} else {
								this.isAnd = 2;
							}
							this.value = exp.substring(target + (this.equal ? 1 : 2), exp.length() - (this.isAnd == 2 ? 0 : 1));
						}
						
						public void setNext(Node node) {
							this.next = node;
						}
						
						public boolean compute() {
							boolean ret = compare(value, result.get(key)) == equal;
							if (next == null) {
								return ret;
							} else if (isAnd == 0) {
								return ret && next.compute();
							} else {
								return ret || next.compute();
							}
						}
						
						private boolean compare(String vlaue, Object v) {
							if ("null".equals(value)) {
								return v == null;
							}
							if (v == null) {
								return false;
							}
							return value.equals(v.toString());
						}
					}
					
					if (expect == null || "".equals(expect))
						return true;
					
					Node top = null;
					Node cur = null;
					String surplus = expect;
					
					while (surplus.length() > 0) {
						int andIndex = surplus.indexOf("&");
						int orIndex = surplus.indexOf("|");
						
						int index = 0;
						if (andIndex != -1 && (andIndex < orIndex || orIndex == -1)) {
							index = andIndex;
						} else if (orIndex != -1) {
							index = orIndex;
						} else {
							index = surplus.length() - 1;
						}
						
						String target = surplus.substring(0, index + 1);
						if (top == null) {
							top = new Node(target);
							cur = top;
						} else {
							cur.setNext(new Node(target));
							cur = cur.next;
						}
						surplus = surplus.substring(index + 1);
					}
					return top.compute();
				}
			};
		}
		
		
		
		/**
		 * 根据接口, 发送请求, 获取结果
		 */
		public HttpAPIResult doRequest(String interfaceId, String param, Context ctx) {
			InterfaceEntityRepository interfaceEntityDao = SpringUtil.getSpringBean(InterfaceEntityRepository.class);
			InterfaceEntity entity = interfaceEntityDao.findOne(interfaceId);
			if (entity == null) {
			    ExceptionUtil.throwRuntimeException("接口：[" + interfaceId + "]并不存在");
			}
			
			HttpAPI api = Permanent.cloneFrom(entity, HttpAPI.class);
			api.setClient(client);
			api.setHost(host);
			
			// 收集相关信息
			ctx.setCurrentName(entity.getName());
			
			// 格式化参数信息
			String params[] = format(param, ctx);
			int index = 0;
			for (InterfaceParam p : entity.getParams()) {
			    ctx.addCurrentParam(p.getKey(), params[index]);
			    index++;
			}
			
			return api.execute(params);
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
				if (key.startsWith("{") && key.endsWith("}")) {
				    Object value = ctx.get(key.substring(1, key.length() - 1));
				    if (value == null)
				        ExceptionUtil.throwRuntimeException("不存在的引用: " + key);
				    result[i] = value.toString();
				}
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
		this.globalExp = t.getGlobalExp();
		return this;
	}

	@Override
	public TestCaseRepository getRepository() {
		return SpringUtil.getSpringBean(TestCaseRepository.class);
	}
}
