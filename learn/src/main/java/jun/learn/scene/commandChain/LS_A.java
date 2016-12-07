package jun.learn.scene.commandChain;
public class LS_A extends AbstractLS {

	@Override
	public String getOperateParam() {
		return A_PARAM;
	}

	@Override
	public String echo(CommandVO vo) {
		return "ls_a echo something";
	}
	
}