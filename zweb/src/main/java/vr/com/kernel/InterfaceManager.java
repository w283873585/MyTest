package vr.com.kernel;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

import vr.com.data.dao.InterfaceEntiyDao;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;
import vr.com.util.text.SplitUtil;
import vr.com.util.text.SplitUtil.FragProvider;

public class InterfaceManager {
	
	private boolean disabled = true;
	private InterfaceEntity entity = new InterfaceEntity();
	
	public void process(RequestBody box) {
		FragProvider provider = SplitUtil.split(box.getUrl(), "\\s+");
		
		if (provider.size() > 1) {
			disabled = false;
			
			box.setUrl(provider.get());
			entity.setUrl(box.getUrl().replaceFirst("https?://[^/]*(:[0-9]+)?", ""));
			entity.setName(provider.get());
			entity.setDesc(provider.get());
			
			addParams(box.getParams());
		}
	}
	
	private void addParams(List<RequestBodyParam> params) {
		List<InterfaceParam> iParams = new ArrayList<InterfaceParam>();
		
		for (RequestBodyParam obj : params) {
			FragProvider provider = SplitUtil.split(obj.getKey(), "\\s+");
			obj.setKey(provider.get());
			InterfaceParam param = new InterfaceParam();
			param.setKey(obj.getKey());
			param.setDesc(provider.get());
			param.setConstraint(obj.getProcessorKeys());
			iParams.add(param);
		}
		entity.setParams(iParams);
	}
	
	public void addResult(String result) {
		if (disabled) return;
		
		List<InterfaceParam> resultParam = new ArrayList<InterfaceParam>();
		JSONObject responseObj = JSONObject.parseObject(result);
		for (String key : responseObj.keySet()) {
			InterfaceParam param = new InterfaceParam();
			param.setKey(key);
			resultParam.add(param);
		}
		entity.setResults(resultParam);
		
		// 持久化数据
		InterfaceEntiyDao interfaceEntityDao = new InterfaceEntiyDao();
		if (!interfaceEntityDao.existInterface(entity.getUrl()))
			interfaceEntityDao.insert(entity);
		else
			interfaceEntityDao.updateByUrl(entity.getUrl(), entity);
	}
}
