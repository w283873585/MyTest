package vr.com.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sell/helper")
public class SellAction {
	
	@RequestMapping("/main")
	public String toMain() {
		return "sell/main";
	}
	
	@ResponseBody
	@RequestMapping("/customer/load")
	public String loadCustomerInfo() {
		return null;
	}
}
