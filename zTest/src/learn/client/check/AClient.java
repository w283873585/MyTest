package learn.client.check;

import com.alibaba.fastjson.JSONObject;


public class AClient {
	public static void main(String[] args) {
		BaseResCode resp = new BaseResCode();
		
		RequestActivityUserMobileRegisterVO req = new RequestActivityUserMobileRegisterVO();
		req.setMobile("12rsa3");
		req.setLoginPassword("123rsa456");
		req.setSmsCode("arsaaaa");
		
		RequestActivityUserMobileRegisterVO req1 = new RequestActivityUserMobileRegisterVO();
		req1.setMobile("123");
		req1.setSmsCode("123456");
		
		RequestActivityUserMobileRegisterVO req2 = new RequestActivityUserMobileRegisterVO();
		req2.setSmsCode("123456");
		
		
		System.out.println(VR_CheckUtil.check(req, resp));
		System.out.println(JSONObject.toJSONString(req));
		System.out.println(JSONObject.toJSONString(resp));
		System.out.println(VR_CheckUtil.check(req1, resp));
		System.out.println(JSONObject.toJSONString(req1));
		System.out.println(JSONObject.toJSONString(resp));
		System.out.println(VR_CheckUtil.check(req2, resp));
		System.out.println(JSONObject.toJSONString(req2));
		System.out.println(JSONObject.toJSONString(resp));
	}
}
