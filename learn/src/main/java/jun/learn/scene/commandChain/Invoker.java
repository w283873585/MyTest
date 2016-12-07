package jun.learn.scene.commandChain;

public class Invoker {
	public String exec(String _commandStr) {
		String result = "";
		
		CommandVO vo = new CommandVO(_commandStr);
		
		if (CommandEnum.getNames().contains(vo.getCommandName())) {
			String className = CommandEnum.valueOf(vo.getCommandName()).getValue();
			
			Command command = null;
			try {
				command = (Command) Class.forName(className).newInstance();
				result = command.execute(vo);
			} catch (Exception e) {
				e.printStackTrace();
			} 
		} else {
			result = "无法执行命令，请检查命令格式";
		}
		return result;
	}
}
