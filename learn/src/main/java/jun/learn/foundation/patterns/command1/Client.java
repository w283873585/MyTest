package jun.learn.foundation.patterns.command1;

public class Client {
	public static void main(String[] args) {
		Leader leader = new Leader();
		Receiver zhangsan = new ZhangSan();
		Command command = new GoDieCommand(zhangsan);
		leader.setCommand(command);
		leader.action();
		command = new HmyCommand(zhangsan);
		leader.setCommand(command);
		leader.action();
	}
}
