package vr.com.kernel;

import java.util.ArrayList;
import java.util.List;

import vr.com.data.dao.InterfaceEntiyDao;
import vr.com.pojo.InterfaceEntity;
import vr.com.pojo.InterfaceParam;

public class InterfaceManager {
	
	private static final String separator = "->";
	
	public static void process(RequestBody box) {
		String url = box.getUrl();
		List<RequestBody_Param> paramArr = box.getParams();
		
		url = url.trim();
		String expression[] = url.split(separator);
		
		if (expression.length > 1) {
			InterfaceEntity entity = new InterfaceEntity();
			
			box.setUrl(expression[0]);
			String interfaceUrl = url.replaceFirst(".*:[0-9]*", ""); 
			entity.setUrl(interfaceUrl);
			entity.setName(expression[1]);
			
			if (expression.length > 2) entity.setDesc(expression[2]);
			
			List<InterfaceParam> iParams = new ArrayList<InterfaceParam>();
			for (RequestBody_Param obj : paramArr) {
				InterfaceParam param = new InterfaceParam();
				String keyExpressions[] = obj.getKey().trim()
						.split(separator);
				param.setKey(keyExpressions[0]);
				param.setConstraint(obj.getProcessorKeys());
				if (keyExpressions.length > 1) {
					obj.setKey(keyExpressions[0]);
					param.setDesc(keyExpressions[1]);
				}
				iParams.add(param);
			}
			entity.setParams(iParams);
			entity.setResults(new ArrayList<InterfaceParam>());
			
			// 持久化数据
			InterfaceEntiyDao interfaceEntityDao = new InterfaceEntiyDao();
			if (!interfaceEntityDao.existInterface(interfaceUrl))
				interfaceEntityDao.insert(entity);
			else
				interfaceEntityDao.updateByUrl(interfaceUrl, entity);
		}
	}
}
