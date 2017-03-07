package jun.learn.scene.softChain.kernel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class RequestManager{
	
	public static ReqResult invoke(Map<String, Object> data, Method handle) {
		
		final ReqResult result = new ReqResult();
		MetaData metaData = new MetaData(handle);
		ReqData reqData = new ReqData(data);
		for (ReqFilter invoker : ReqFilters.values()) {
			invoker.filter(metaData, reqData, result);
		}
		
		if (!result.hasCommit()) {
			result.success();
			reqData.each(new Carrier() {
				@Override
				public void put(String key, Object val) {
					result.attach(key, val);
				}
			});
		}
		
		return result;
	}
	
	
	public static ReqResult executeForResponse(Map<String, Object> data, Method handle) {
		ReqResult result = new ReqResult();
		// 响应请求时, 默认已成功(因为不通过验证, 到不了这)
		result.success();
		
		List<String> keys = Arrays.asList("aeskey", "aesIv", "packageId");
		for (Entry<String, Object> entry : data.entrySet()) {
			if (!keys.contains(entry.getKey()))
				result.attach(entry.getKey(), entry.getValue());
		}
		
		ReqData reqData = new ReqData(data);
		MetaData metaData = new MetaData(handle);
		ReqFilters.encrypt.filter(metaData, reqData, result);
		
		return result;
	}
}
