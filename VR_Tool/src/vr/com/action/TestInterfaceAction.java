package vr.com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import vr.com.util.ClientUtil;
import vr.com.util.ParamsMapUtil;
import vr.com.util.ParamsMapUtil.MyMap;
import vr.com.util.ParamsMapUtil_dev;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vr.show.client.common.VR_HttpClientUtils;

@Controller
@RequestMapping("/my")
public class TestInterfaceAction {
	
	@RequestMapping("/testRest")
	public String toTestRest(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("params", ClientUtil.getParamInfo());
		return "zTestRest";
	}
	
	@RequestMapping(value="/send",  produces = "text/html;charset=utf-8")
	@ResponseBody
	public String send(HttpServletRequest request, HttpServletResponse response,
			String keyWord, String interfaceUrl, String paramsInfo, 
			@RequestParam(defaultValue="false")boolean isDev) {
		String result = "";
		try {
			JSONArray json = JSONArray.parseArray(paramsInfo);
			if (isDev) {
				ParamsMapUtil_dev.MyMap m = ParamsMapUtil_dev.newMap();
				for (int i = 0; i < json.size(); i++) {
					JSONObject obj = json.getJSONObject(i);
					m.putA(obj.getString("key"), obj.get("value"), obj.getBoolean("needEncrypt"), obj.getBoolean("needEncode"));
				}
				result = VR_HttpClientUtils.devClientPost(m, keyWord, request, response, interfaceUrl);
			} else {
				MyMap m = ParamsMapUtil.newMap();
				for (int i = 0; i < json.size(); i++) {
					JSONObject obj = json.getJSONObject(i);
					m.putA(obj.getString("key"), obj.get("value"), obj.getBoolean("needEncrypt"), obj.getBoolean("needEncode"));
				}
				result = ClientUtil.clientPost(m, keyWord, request, response, interfaceUrl);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = e.toString();
		}
		return result;
	}
}