package vr.com.action;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/sell/helper")
public class SellAction {
	
	/**
	 * 销售小帮手首页 
	 */
	@RequestMapping("/main")
	public String toMain() {
		/**
		 * 用户模式， 策略模式， 事件模式。
		 * 
		 * 1. 展示最近的事件， 最近活跃的用户
		 * 2. 用户信息录入入口
		 * 3. 用户搜索入口
		 * 4. 用户详情入口
		 * 5. 策略录入入口
		 * 6. 日历展示事件
		 */
		return "sell/main";
	}
	
	/**
	 * 录入或者修改客户信息 
	 */
	@ResponseBody
	@RequestMapping("/customer/load")
	public String loadCustomerInfo() {
		return null;
	}
	
	/**
	 * 获取客户信息 
	 */
	@ResponseBody
	@RequestMapping("/customer/detail/{id}")
	public String getCustomerInfo() {
		return null;
	}
	
	/**
	 * 客户消费记录录入
	 */
	@ResponseBody
	@RequestMapping("/customer/record")
	public String addCustomerRecord() {
		return null;
	}
	
	/**
	 * 客户策略录入或更新
	 */
	@ResponseBody
	@RequestMapping("/my/strategy")
	public String addStrategy() {
		return null;
	}
}
