package jun.learn.scene.commandChain;

public class LSCommand extends Command{

	@Override
	public String execute(CommandVO vo) {
		CommandName firstNode = super.buildChain(AbstractLS.class).get(0);
		return firstNode.handleMessage(vo);
	}
}
