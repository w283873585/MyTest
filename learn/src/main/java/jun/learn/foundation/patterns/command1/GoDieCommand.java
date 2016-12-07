package jun.learn.foundation.patterns.command1;

public class GoDieCommand extends Command{

	public GoDieCommand(Receiver receiver) {
		super(receiver);
	}

	@Override
	public void execute() {
		receiver.goDie();
	}

}
