package vr.com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vr.com.command.Command;
import vr.com.command.CommandFactory;
import vr.com.command.CommandHistory;
import vr.com.command.CommandResult;

import com.alibaba.fastjson.JSONObject;

@Controller
@RequestMapping("/command")
public class CommandAction {
	
	@RequestMapping("/toCommand")
	public String toCommand(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("commands", JSONObject.toJSONString(CommandFactory.getCommandNames()));
		request.setAttribute("historyCommands", JSONObject.toJSONString(CommandHistory.getCommands()));
		return "command";
	}
	
	@RequestMapping(value="/doCommand",  produces = "text/html;charset=utf-8")
	@ResponseBody
	public String send(HttpServletRequest request, HttpServletResponse response,
			String commandName, String paramsInfo) {
		
		Command command = CommandFactory.getCommand(commandName);
		CommandResult result = command.exec(paramsInfo);
		
		return result.toString();
	}
}