package vr.com.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import vr.com.util.VR_MD5Util;
import vr.com.util.rsa.RSA_vrshow;

public class Client_vrshow extends ClientWithProcessor {
	private String authMd5 = "92A864886F70D010101050101010500048202613082025D02010002818";
	private String ipAddress = "127.0.0.1";
	private String browserName = "webkit";
	@SuppressWarnings("unused")
	private String cookieName = "VR_SHOW_CLIENT_EQU_" + browserName;
	private String cookieValue = "12345678910111213141516171819202";
	private String version = "1.0";
	private String clientType = "1";
	
	@Override
	public RequestProcessor getRequestProcessor() {
		
		return new RequestProcessor() {
			
			@Override
			public Request process(Request request) {

				// 重新组织参数
				Map<String, Object> params = new HashMap<String, Object>();
				Map<String, Object> parmsMap = request.getParams();
				request.clearParam();
				
				String jsonData = "-1";
				if (parmsMap != null && parmsMap.size() > 0) {
					jsonData = JSON.toJSONString(parmsMap);
				}
				
				// 对业务参数进行MD5鉴权加密
				StringBuffer authorization = new StringBuffer(jsonData).append(authMd5);
				String auth = VR_MD5Util.MD5(authorization.toString());

				// 身份标识
				String identificationCode = cookieValue + "_" + browserName;
				identificationCode = RSA_vrshow.encrypt(identificationCode);

				params.put("jsonData", jsonData);
				params.put("ipAddr", ipAddress);
				params.put("version", version);
				params.put("authorization", auth);
				params.put("clientType", clientType);
				params.put("identificationCode", identificationCode.toString());
				request.setParam(params);
				
				return request;
			}
		};
		
	}

	@Override
	public String getName() {
		return "vrshow";
	}

}
