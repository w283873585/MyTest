package jun.learn.scene.softChain.kernel;

import java.lang.reflect.Method;
import java.util.Map;

import jun.learn.scene.softChain.annotation.ReqEncryption;
import jun.learn.scene.softChain.annotation.ReqParamRestrict;
import jun.learn.scene.softChain.annotation.ReqParamRestricts;
import jun.learn.scene.softChain.annotation.ReqParamVerifyChain;
import jun.learn.scene.softChain.annotation.ReqParamVerifyChain.Result;

public class MetaData {
	
	private ReqParamRestricts restricts  = null;
	private ReqEncryption reqEncryption = null;
	
	public MetaData(Method method) {
		this.restricts = method.getDeclaredAnnotation(ReqParamRestricts.class);
		this.reqEncryption = method.getDeclaredAnnotation(ReqEncryption.class);
	}
	
	public boolean reqEncrypt() {
		return reqEncryption != null && reqEncryption.requestEncrypt();
	}
	
	public boolean responseEncrypt() {
		return reqEncryption != null && reqEncryption.responseEncrypt();
	}
	
	public void checkParams(Map<String, Object> data, ReqResult result) {
		if (restricts == null) 
			return;
		
		for (ReqParamRestrict restrict : restricts.value()) {
			String key = restrict.key();
			ReqParamVerifyChain chain = ReqParamVerifyChain.bulidChain(restrict.value(), data.get(key));
			Result r = chain.proceed();
			if (!r.isSuccess()) {
				result.failed(r.getResult().toString());
				return;
			}
			
			// update the target value
			data.put(key, chain.getTarget());
		}
	}
}
