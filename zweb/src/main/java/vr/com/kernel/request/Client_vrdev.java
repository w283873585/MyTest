package vr.com.kernel.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import vr.com.util.VR_MD5Util;

public class Client_vrdev extends ClientWithProcessor {
	private String authMd5 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQC9QQzNcUrWngZJwNXGwE0EyQVIVqHAMgsqro6yRFD2zbvx34Rvb6XjFjEqqDEB0D2hJOoe9ezHbGMlhqdtoIrQCr13rnEqJ11HIvuaD4SW6Sos2JnWnFZNR3XJqYZIF2Hv3+hq8LSFyednpMMq3gDzW4VfKuZ0+hBFSoAoeJewIDAQAB";
	private String clientType = "1";
	private String ipAddress = "127.0.0.1";
	
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

				params.put("jsonData", jsonData);
				params.put("ipAddr", ipAddress);
				params.put("authorization", auth);
				params.put("clientType", clientType);
				params.put("identificationCode", "-1");
				request.setParam(params);
				
				return request;
			}
		};
	}

	@Override
	public String getName() {
		return "vrdev";
	}
}
