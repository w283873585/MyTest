package vr.com.kernel;

import java.util.ArrayList;
import java.util.List;

import vr.com.data.dao.InterfaceEntiyDao;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;

public class InterfaceManager {
	
	private boolean disabled = true;
	
	private InterfaceEntity entity = new InterfaceEntity();
	
	public void process(RequestBody box) {
		SplitHelper util = new SplitHelper(box.getUrl());
		
		if (!util.isCommon()) {
			
			disabled = false;
			
			box.setUrl(util.next());
			entity.setUrl(box.getUrl().replaceFirst(".*:[0-9]*", ""));
			entity.setName(util.next());
			entity.setDesc(util.next());
			
			addParams(box.getParams());
		}
	}
	
	private void addParams(List<RequestBodyParam> params) {
		List<InterfaceParam> iParams = new ArrayList<InterfaceParam>();
		
		for (RequestBodyParam obj : params) {
			SplitHelper util = new SplitHelper(obj.getKey());
			obj.setKey(util.next());
			
			InterfaceParam param = new InterfaceParam();
			param.setKey(obj.getKey());
			param.setDesc(util.next());
			param.setConstraint(obj.getProcessorKeys());
			iParams.add(param);
		}
		
		entity.setParams(iParams);
	}
	
	public void addResult(String result) {
		if (disabled) return;
		
		// dosomething
		entity.setResults(new ArrayList<InterfaceParam>());
	}
	
	public void start() {
		if (disabled) return;
		
		// 持久化数据
		InterfaceEntiyDao interfaceEntityDao = new InterfaceEntiyDao();
		if (!interfaceEntityDao.existInterface(entity.getUrl()))
			interfaceEntityDao.insert(entity);
		else
			interfaceEntityDao.updateByUrl(entity.getUrl(), entity);
	}
	
	public static class SplitHelper{
		private static final String separator = "->";
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
		
		public boolean isCommon() {
			return body.length <= 1;
		}
	}
}
