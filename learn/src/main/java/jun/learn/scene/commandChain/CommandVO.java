package jun.learn.scene.commandChain;

import java.util.ArrayList;
import java.util.List;

public class CommandVO {
	
	public static final String DIVIDE_FALG = " ";
	public static final String PREFIX = "-";
	
	private String commandName = "";
	private List<String> params = new ArrayList<String>();
	private List<String> datas = new ArrayList<String>();
	
	public CommandVO(String commandStr) {
		if (commandStr == null || commandStr.length() == 0) {
			System.out.println("命令非法");
			return;
		}	
		
		String[] complexStr = commandStr.split(DIVIDE_FALG);
		this.commandName = complexStr[0];
		for (int i = 1; i < complexStr.length; i++) {
			String str = complexStr[i];
			if (str.indexOf(PREFIX) == 0) {
				params.add(str.replace(PREFIX, "").trim());
			} else {
				datas.add(str.trim());
			}
		}
	}
	
	public List<String> getParam() {
		return params;
	}
	
	public List<String> getData() {
		return datas;
	}
	
	public String getCommandName() {
		return commandName;
	}
}
