package jun.learn.foundation.patterns.command1;

public class HmyCommand extends Command{

	public HmyCommand(Receiver receiver) {
		super(receiver);
	}

	@Override
	public void execute() {
		receiver.goWork();
		receiver.goMeeting();
		receiver.goDie();
	}
	
	
}
