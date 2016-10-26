package vr.com.kernel.request;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import vr.com.kernel.request.HttpUtil.MyResponse;
import vr.com.util.security.CommonConstant;
import vr.com.util.security.VR_Encrypt_Util;
import vr.com.util.security.VR_Security_Util;

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
	
	@Override
	protected MyResponse processResponse(MyResponse res) {
		MyResponse result = new MyResponse();
		result.e = res.e;
		result.code = res.code;
		result.header = res.header;
		try {
			String data = JSONObject.parseObject(res.toString()).getString("msg");
			data = VR_Encrypt_Util.aesDecode(data, CommonConstant.AES_key, CommonConstant.AES_iv);
			result.data = data.getBytes("utf-8");
		} catch (Exception e) {
			e.printStackTrace();
			return res;
		}
		return result;
	}
}
