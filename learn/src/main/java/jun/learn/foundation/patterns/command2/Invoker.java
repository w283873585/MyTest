package jun.learn.foundation.patterns.command2;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
	private List<Command> command_list = new ArrayList<Command>();
	
	public void add(Command command) {
		command_list.add(command);
	}
	
	public void invoke() {
		for (Command command : command_list) {
			command.execute();
		}
	}
}
