package learn.pattern.client.commandChain;

public class LS extends AbstractLS{

	@Override
	public String getOperateParam() {
		return DEFAULT_PARAM;
	}

	@Override
	public String echo(CommandVO vo) {
		return "ls echo something";
	}
}
