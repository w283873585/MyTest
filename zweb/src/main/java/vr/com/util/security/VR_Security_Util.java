package vr.com.util.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

public class VR_Security_Util {
	
	
	/**
	 * 解密流程
	 * 
	 * rsa解密aeskey, rsa解密aesIv
	 * 
	 * aes解密msg
	 */
	public static BaseResponse decode(Map<Object, Object> reqParam) {
		
		BaseResponse result = new BaseResponse();
		
		if (reqParam == null || reqParam.size() == 0) {
			result.setResCode(ResCodeConstans.parmsIsNull);
			result.setResDesc("未获取到任何请求参数");
            return result;
        }
		
		String msg = "", 
			aesIv = "", 
			aeskey = "", 
			packageId = "";
		
		
		// 验证并获取相关参数
		try {
			msg = checkAndGetValue("msg", reqParam);
			aesIv = checkAndGetValue("aesIv", reqParam);
			aeskey = checkAndGetValue("aeskey", reqParam);
			packageId = checkAndGetValue("packageId", reqParam);
		} catch (Exception e) {
			result.setResCode(ResCodeConstans.parmsIsNull);
			result.setResDesc("[" + e.getMessage() + "]参数为空或者不合法");
			return result;
		}
		
		
		// rsa解密
		try {
			aeskey = VR_Encrypt_Util.rsaDecode(aeskey);
			aesIv = VR_Encrypt_Util.rsaDecode(aesIv);
			log("############请求报文解密后aeskey="  + aeskey);
			log("############请求报文解密后aesIv="  + aesIv);
		} catch (Exception e) {
			result.setResCode(ResCodeConstans.parmsIsNull);
			result.setResDesc("rsa解密失败");
			return result;
		}
		
		
		// aes解密
		try {
			msg = VR_Encrypt_Util.aesDecode(msg, aeskey, aesIv);
			log("############请求报文 解密后msg"  + msg);
		} catch (Exception e) {
			result.setResCode(ResCodeConstans.parmsIsNull);
			result.setResDesc("aes解密失败");
			return result;
		}
		
		
		// 解密成功
		result.setAesIv(aesIv);
		result.setAeskey(aeskey);
		result.setPackageId(packageId);
		result.setBody(JSON.parseObject(msg));
		result.setResCode(ResCodeConstans.success);
		
		return result;
	}
	
	// TODO 异常传递
	private static String checkAndGetValue(String key, Map<Object, Object> reqParam) throws Exception {
		try {
			String value = ((String[]) reqParam.get(key))[0];
			
			log("############请求报文 " + key + "="  + value);
			
			if (null == value || "null".equals(value) || "".equals(value))
				throw new Exception(key);
			
			return value;
		} catch (Exception e) {
			throw new Exception(key);
		}
	}
	
	// 日志
	private static void log(String msg) {
		System.out.println(msg);
	}
	
	
	/**
	 * 加密流程
	 * 
	 * msg, aes加密
	 * 
	 * 输出
	 * {"msg":"47140BFCCEDBFE37B53E7225699D065C69FDE709ECC5082B4F7ABA8490B3404B","length":"32","packageId":"1271"}
	 * 
	 */
	public static JSONObject encode(String msg, HttpServletRequest request) {
		
		JSONObject result = new JSONObject();
		
		if (msg == null 
			|| request == null
			|| request.getAttribute("aeskey") == null 
			|| request.getAttribute("aesIv") == null) {
			
			// 请求必须是加密请求, 不然是加密不了响应信息的
			result.put("resDesc", "请求参数发生错误");
			return result;
		}
		
		byte[] encrptData;
		try {
			encrptData = VR_Encrypt_Util.aesEncodeToByte(msg, 
					request.getAttribute("aeskey").toString(),
					request.getAttribute("aesIv").toString());
		} catch (Exception e) {
			e.printStackTrace();
			result.put("resDesc", "aes加密失败");
			return result;
		}
		
		result.put("msg", Base16.encode(encrptData));
		result.put("length", encrptData.length);
		result.put("packageId", request.getAttribute("packageId"));
		
		return result;
	}
	
	
	
	public static class BaseResponse {
		private String resCode;
		private String resDesc;
		
		private String aeskey;
		private String aesIv;
		private String packageId;
		
		private JSONObject body;
		
		public String getResCode() {
			return resCode;
		}
		public void setResCode(String resCode) {
			this.resCode = resCode;
		}
		public String getResDesc() {
			return resDesc;
		}
		public void setResDesc(String resDesc) {
			this.resDesc = resDesc;
		}
		public JSONObject getBody() {
			return body;
		}
		public void setBody(JSONObject body) {
			this.body = body;
		}
		public String getAeskey() {
			return aeskey;
		}
		public void setAeskey(String aeskey) {
			this.aeskey = aeskey;
		}
		public String getAesIv() {
			return aesIv;
		}
		public void setAesIv(String aesIv) {
			this.aesIv = aesIv;
		}
		public String getPackageId() {
			return packageId;
		}
		public void setPackageId(String packageId) {
			this.packageId = packageId;
		}
	}
}
