package vr.com.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import vr.com.aop.TestAopI;
import vr.com.command.CommandResult;
import vr.com.command.support.CommandManager;
import vr.com.data.springData.repository.CommandResultRepository;

@Controller
@RequestMapping("/commands")
public class CommandAction implements InitializingBean{
	
	@Autowired
	private CommandResultRepository commandResultRepository;
	
	@Autowired 
	TestAopI aop;
	
	@RequestMapping("/")
	public String toCommand(HttpServletRequest request, HttpServletResponse response) {
		request.setAttribute("commands", commandResultRepository.findAll());
		return "command2";
	}
	
	@ResponseBody
	@RequestMapping("/result")
	public String send(
			HttpServletRequest request, 
			HttpServletResponse response,
			String commandInfo) {
		CommandResult result = CommandManager.execute(commandInfo);
		return result.getResult();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		aop.doSomething();
	}
}