package jun.learn.scene.softChain.annotation;

import jun.learn.scene.softChain.annotation.Chain.Result;

public class Client {
	public static void main(String[] args) {
		Object value = "";
		Chain chain = Chain.bulidChain(ReqParamRestrictType2.values(), value);
		Result r = chain.proceed();
		if (!r.isSuccess()) {
			return;
		} else {
			System.out.println(chain.getTarget());
		}
	}
}
