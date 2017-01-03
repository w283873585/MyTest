package jun.learn.scene.softChain.kernel;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Map;

import jun.learn.scene.softChain.annotation.ReqEncryption;
import jun.learn.scene.softChain.annotation.ReqParamRestrict;
import jun.learn.scene.softChain.annotation.ReqParamRestrictType;
import jun.learn.scene.softChain.annotation.ReqParamRestrictType.Result;
import jun.learn.scene.softChain.annotation.ReqParamRestricts;

public class MetaData {
	
	public MetaData(Method method) {
		this.restricts = method.getDeclaredAnnotation(ReqParamRestricts.class);
		this.reqEncryption = method.getDeclaredAnnotation(ReqEncryption.class);
	}
	
	private ReqParamRestricts restricts  = null;
	private ReqEncryption reqEncryption = null;
	
	public boolean reqEncrypt() {
		return reqEncryption != null && reqEncryption.requestEncrypt();
	}
	
	public boolean responseEncrypt() {
		return reqEncryption != null && reqEncryption.responseEncrypt();
	}
	
	public void checkParams(Map<String, Object> data, ReqResult result) {
		for (ReqParamRestrict restrict : restricts.value()) {
			Object paramValue = data.get(restrict.key());
			ReqParamRestrictType[] reqParamRestrictTypes = restrict.value();
			sort(reqParamRestrictTypes);
			for (ReqParamRestrictType type : reqParamRestrictTypes) {
				Result r = type.check(paramValue);
				if (!r.isSuccess()) {
					result.failed("[" + restrict.key() + "]" + r.getResult());
					return;
				} else if (type == ReqParamRestrictType.Rsa){
					data.put(restrict.key(), r.getResult());
				}
			}
		}
	}
	
	private void sort(ReqParamRestrictType[] types) {
		Arrays.sort(types, new Comparator<ReqParamRestrictType>() {
			@Override
			public int compare(ReqParamRestrictType o1, ReqParamRestrictType o2) {
				return o1.ordinal() - o2.ordinal();
			}
		});
	}
}
