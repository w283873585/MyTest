package vr.com.kernel2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

import vr.com.kernel.processor.ValueProcessor;
import vr.com.kernel.processor.ValueProcessorFactory;
import vr.com.kernel.request.Client;
import vr.com.kernel.request.ClientFactory;
import vr.com.kernel.request.Request;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;

public class HttpAPI {
	
	public HttpAPI(String name, String url, String clientName) {
		this.url = url;
		this.name = name;
		this.client = ClientFactory.getClient(clientName);
	}
	
	private String url;
	private String name;
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
	
	public String invoke(String... params) {
		Map<String, Object> param = new HashMap<String, Object>();
		
		forEach(new BiConsumer<String, ValueProcessor>() {
			int index = 0;
			@Override
			public void accept(String key, ValueProcessor processor) {
				param.put(key, processor.process(params[index++]));
			}
		});
		
		return client.httpRequest(new Request(false, url, param)).toString();
	}
	
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
	
	private void forEach(BiConsumer<String, ValueProcessor> consumer) {
		for (int i = 0; i < keys.size(); i++) {
			consumer.accept(keys.get(i), processors.get(i));
		}
	}
	
	public static HttpAPI cloneFrom(InterfaceEntity entity) {
		HttpAPI api = new HttpAPI(entity.getName(), entity.getUrl(), "");
		for (InterfaceParam iParam : entity.getParams()) {
			api.add(iParam.getKey(), iParam.getConstraint(), iParam.getDesc());
		}
		return api;
	}
}
