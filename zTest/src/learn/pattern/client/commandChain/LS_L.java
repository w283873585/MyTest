package learn.pattern.client.commandChain;
public class LS_L extends AbstractLS {

	@Override
	public String getOperateParam() {
		return L_PARAM;
	}

	@Override
	public String echo(CommandVO vo) {
		return "ls_l echo something";
	}
	
}