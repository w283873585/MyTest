package vr.com.kernel.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;

import vr.com.util.security.CommonConstant;
import vr.com.util.security.VR_Encrypt_Util;

public class Client_vrsoft extends ClientWithProcessor {
	private String packageId = "1271";
	private String equ = "02719220951061609410839358932208";
	private String clientType = "6";
	
	@Override
	public RequestProcessor getRequestProcessor() {
		
		return new RequestProcessor() {
			
			@Override
			public Request process(Request request) {
				
				// 重新组织参数
				Map<String, Object> params = new HashMap<String, Object>();
				
				Map<String, Object> parmsMap = request.getParams();
				request.clearParam();
				
				// 添加默认参数
				parmsMap.put("equ", equ);
				parmsMap.put("clientType", clientType);
				
				String jsonData = JSON.toJSONString(parmsMap);
				
				try {
					// 对业务参数进行aes加密
					String msg = VR_Encrypt_Util.aesAndBase16Encode(jsonData, CommonConstant.AES_key, CommonConstant.AES_iv);
					
					params.put("packageId", packageId);
					params.put("aeskey", VR_Encrypt_Util.rsaEncode1(CommonConstant.AES_key));
					params.put("aesIv", VR_Encrypt_Util.rsaEncode1(CommonConstant.AES_iv));
					params.put("msg", msg);
					request.setParam(params);
				} catch (Exception ignore) {}
				
				return request;
			}
		};
	}

	@Override
	public String getName() {
		return "vrsoft";
	}
}
