package jun.learn.scene.res;

public class Client {
	public static void main(String[] args) {
		/**
		 * response设置error信息,
		 *  
		 */
		ResponseAmEquimentAuth resp = new ResponseAmEquimentAuth();
		resp.error(Hello.noInUse);
		
		/**
		 * vs
		 */
		
		resp.setResCode("0");
		resp.setResDesc("no in use");
	}
}
