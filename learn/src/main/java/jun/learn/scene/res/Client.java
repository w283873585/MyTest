package jun.learn.scene.res;

import jun.learn.scene.res.ResponseAmEquimentAuth.Hello;

public class Client {
	public static void main(String[] args) {
		ResponseAmEquimentAuth resp = new ResponseAmEquimentAuth();
		
		/**
		 * response设置error信息,
		 * 子类限制响应信息类型
		 */
		resp.res(Hello.noInUse);
		// Hello.noInUse.error(resp);
		
		/**
		 * vs
		 */
		
		resp.setResCode("0");
		resp.setResDesc("no in use");
	}
}
