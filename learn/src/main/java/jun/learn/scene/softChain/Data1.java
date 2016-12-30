package jun.learn.scene.softChain;

import jun.learn.scene.softChain.annotation.ReqParamRestricts;

import java.util.Map;

import jun.learn.scene.softChain.annotation.ReqEncryption;
import jun.learn.scene.softChain.annotation.ReqParamRestrict;
import jun.learn.scene.softChain.annotation.ReqParamRestrictType;
import jun.learn.scene.softChain.annotation.ReqParamRestrictType.Result;

public class Data1 {
	
	private Map<String, Object> data = null;
	
	private ReqParamRestricts restricts  = null;
	private ReqEncryption reqEncryption = null;
	
	public boolean check() {
		for (ReqParamRestrict restrict : restricts.value()) {
			Object paramValue = data.get(restrict.key());
			for (ReqParamRestrictType type : restrict.value()) {
				Result r = type.check(paramValue);
				if (!r.isSuccess()) {
					return false; 
					// new Hint(r.isSuccess(), "[" + restrict.key() + "]" + r.getResult());
				} else if (type == ReqParamRestrictType.Rsa){
					data.put(restrict.key(), r.getResult());
				}
			}
		}
		return false;
	}
	
	public boolean reqEncrypt() {
		return reqEncryption.requestEncrypt();
	}
	
	public boolean responseEncrypt() {
		return reqEncryption.responseEncrypt();
	}
}
