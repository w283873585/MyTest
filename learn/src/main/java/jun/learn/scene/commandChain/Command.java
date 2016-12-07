package jun.learn.scene.commandChain;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {
	public abstract String execute(CommandVO vo);
	
	protected final List<? extends CommandName> buildChain(Class<? extends CommandName> abstractClass) {
		List<Class> classes = ClassUtils.getSonClass(abstractClass);
		
		List<CommandName> commandNameList = new ArrayList<CommandName>();
		for (Class c : classes) {
			CommandName commandName = null;
			try {
				commandName = (CommandName) Class.forName(c.getName()).newInstance();
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (commandNameList.size() > 0) {
				commandNameList.get(commandNameList.size() - 1).setNext(commandName);
			}
			commandNameList.add(commandName);
		}
		return commandNameList;
	} 
}
