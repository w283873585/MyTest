package vr.com.kernel2.httpAPI;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import org.springframework.data.mongodb.repository.MongoRepository;

import vr.com.data.springData.repository.InterfaceEntityRepository;
import vr.com.kernel.processor.ValueProcessor;
import vr.com.kernel.processor.ValueProcessorFactory;
import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.Request;
import vr.com.kernel2.Permanent;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;
import vr.com.util.SpringUtil;

/**
 *	TODO :
 *		url相对路径与绝对路径的转换
 * 		客户端类型的注入
 * 		持久化的实现
 */
public class HttpAPI implements Permanent<InterfaceEntity>{
	
	public HttpAPI() {}
	
	public HttpAPI(String name, String url, String clientName) {
		this.url = url;
		this.name = name;
		this.client = ClientFactory.getClient(clientName);
	}
	
	private String url;
	private String name;
	
	private String host;
	private Client client;
	
	private List<String> keys = new ArrayList<String>();
	private List<String> descs = new ArrayList<String>();
	private List<ValueProcessor> processors = new ArrayList<ValueProcessor>();
	
	public void add(String key, String processorName) {
		add(key, processorName, null);
	}
	
	public void add(String key, String processorName, String desc) {
		keys.add(key);
		descs.add(desc);
		processors.add(ValueProcessorFactory.getProcessor(processorName));
	}
	
	public void setClient(String clientName) {
		this.client = ClientFactory.getClient(clientName);
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	/**
	 * send http request to the server with some special parameter
	 * and return the result 
	 */
	public HttpAPIResult execute(String... params) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		forEach(new BiConsumer<String, ValueProcessor>() {
			int index = 0;
			@Override
			public void accept(String key, ValueProcessor processor) {
				param.put(key, processor.process(params[index++]));
			}
		});
		
		return new HttpApIResultAdapter(client.httpRequest(new Request(false, host + url, param)));
	}
	
	/**
	 * the convenient method for iterate the keys and processors
	 */
	private void forEach(BiConsumer<String, ValueProcessor> consumer) {
		for (int i = 0; i < keys.size(); i++) {
			consumer.accept(keys.get(i), processors.get(i));
		}
	}
	
	/**
	 * 
	 * implement the Permanent interface
	 */
	public InterfaceEntity toPojo() {
		InterfaceEntity entity = new InterfaceEntity();
		
		entity.setName(this.name);
		List<InterfaceParam> params = new ArrayList<InterfaceParam>();
		entity.setParams(params);
		
		forEach(new BiConsumer<String, ValueProcessor>() {
			int index = 0;
			@Override
			public void accept(String key, ValueProcessor processor) {
				InterfaceParam iParam = new InterfaceParam();
				iParam.setKey(key);
				iParam.setConstraint(processor.getName());
				iParam.setDesc(descs.get(index++));
				params.add(iParam);
			}
		});
		
		return entity;
	}
	
	public HttpAPI cloneFrom(InterfaceEntity entity) {
		this.url = entity.getUrl();
		this.name = entity.getName();
		for (InterfaceParam iParam : entity.getParams()) {
			this.add(iParam.getKey(), iParam.getConstraint(), iParam.getDesc());
		}
		return this;
	}

	@Override
	public MongoRepository<InterfaceEntity, String> getRepository() {
		return SpringUtil.getSpringBean(InterfaceEntityRepository.class);
	}
}
