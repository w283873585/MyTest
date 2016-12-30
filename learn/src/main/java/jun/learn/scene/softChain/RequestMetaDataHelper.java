package jun.learn.scene.softChain;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;

import jun.learn.scene.softChain.annotation.ReqEncryption;
import jun.learn.scene.softChain.annotation.ReqParamRestrict;
import jun.learn.scene.softChain.annotation.ReqParamRestrictType;
import jun.learn.scene.softChain.annotation.ReqParamRestricts;

public class RequestMetaDataHelper{
	
	private ReqParamRestricts restricts;
	private ReqEncryption reqEncryption;
	
	public RequestMetaDataHelper(Object handler) {
		restricts = ((Method) handler).getDeclaredAnnotation(ReqParamRestricts.class);
		reqEncryption = ((Method) handler).getDeclaredAnnotation(ReqEncryption.class);
	}
	
	public boolean requestEncrypt() {
		return reqEncryption != null && reqEncryption.requestEncrypt();
	}

	public boolean responseEncrypt() {
		return reqEncryption != null && reqEncryption.responseEncrypt();
	}
	
	public void each(Carrier c) {
		ReqParamRestrict[] restrictArr = restricts.value();
		
		Arrays.sort(restrictArr, new Comparator<ReqParamRestrict>() {
			@Override
			public int compare(ReqParamRestrict o1, ReqParamRestrict o2) {
				return 0;
			}
		});
		
		for (ReqParamRestrict r : restrictArr) {
			for (ReqParamRestrictType type : r.value()) {
				type.check("");
			}
		}
	}
	
	public static interface Carrier {
		
	}
}
