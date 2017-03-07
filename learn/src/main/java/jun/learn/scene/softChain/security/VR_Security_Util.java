package jun.learn.scene.softChain.security;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import jun.learn.scene.softChain.kernel.ReqData;
import jun.learn.scene.softChain.kernel.ReqResult;

public class VR_Security_Util {
	
	
	/**
	 * 解密流程
	 * 
	 * rsa解密aeskey, rsa解密aesIv
	 * 
	 * aes解密msg
	 */
	public static void decode(Map<String, Object> reqParam, ReqResult result) {
		
		if (reqParam == null || reqParam.size() == 0) {
			result.failed("未获取到任何请求参数");
			result.attach("resCode", ResCodeConstans.parmsIsNull);
            return;
        }
		
		String msg = "", 
			aesIv = "", 
			aeskey = "", 
			packageId = "";
		
		JSONObject data = null;
		
		// 验证并获取相关参数
		try {
			msg = checkAndGetValue("msg", reqParam);
			aesIv = checkAndGetValue("aesIv", reqParam);
			aeskey = checkAndGetValue("aeskey", reqParam);
			packageId = checkAndGetValue("packageId", reqParam);
		} catch (Exception e) {
			result.failed("[" + e.getMessage() + "]参数为空或者不合法");
			result.attach("resCode", ResCodeConstans.parmsIsNull);
            return;
		}
		
		
		// rsa解密
		try {
			aeskey = VR_Encrypt_Util.rsaDecode(aeskey);
			aesIv = VR_Encrypt_Util.rsaDecode(aesIv);
			log("############请求报文解密后aeskey="  + aeskey);
			log("############请求报文解密后aesIv="  + aesIv);
		} catch (Exception e) {
			result.failed("rsa解密失败");
			result.attach("resCode", ResCodeConstans.parmsIsNull);
            return;
		}
		
		
		// aes解密
		try {
			msg = VR_Encrypt_Util.aesDecode(msg, aeskey, aesIv);
			log("############请求报文 解密后msg"  + msg + "\n\n");
		} catch (Exception e) {
			result.failed("aes解密失败");
			result.attach("resCode", ResCodeConstans.parmsIsNull);
            return;
		}
		
		// 主体json解析
		try {
			data = JSON.parseObject(msg);
		}  catch (Exception e) {
			result.failed("参数主体不是json格式.");
			result.attach("resCode", ResCodeConstans.parmsIsNull);
            return;
		}
		
		reqParam.clear();
		reqParam.put("aesIv", aesIv);
		reqParam.put("aesKey", aeskey);
		reqParam.put("packageId", packageId);
		reqParam.put("paramsMap", data);
	}
	
	// TODO 异常传递
	private static String checkAndGetValue(String key, Map<String, Object> reqParam) throws Exception {
		try {
			String value = (String) reqParam.get(key);
			
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
	public static void encode(ReqData reqData, ReqResult result) {
		
		byte[] encrptData = null;
		try {
			encrptData = VR_Encrypt_Util.aesEncodeToByte(result.getAttach(), 
					reqData.getString("aeskey"),
					reqData.getString("aesIv"));
		} catch (Exception e) {
			e.printStackTrace();
			result.attach("encodeDesc", "aes加密失败");
			return;
		}
		
		result.clear();
		result.attach("msg", Base16.encode(encrptData));
		result.attach("length", encrptData.length);
		result.attach("packageId", reqData.get("packageId"));
	}
}
