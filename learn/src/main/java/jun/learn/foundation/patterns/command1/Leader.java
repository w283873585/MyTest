package jun.learn.foundation.patterns.command1;

public class Leader {
	private Command command;
	public void setCommand(Command command){
		this.command = command;
	}
	//指挥开始行动
	public void action(){
		command.execute();
	}
}
