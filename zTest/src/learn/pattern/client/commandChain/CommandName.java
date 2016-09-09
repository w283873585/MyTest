package learn.pattern.client.commandChain;

public abstract class CommandName {
	private CommandName nextOperator;
	public final String handleMessage(CommandVO vo) {
		String result = "";
		if (vo.getParam().size() == 0 || vo.getParam().contains(this.getOperateParam())) {
			result = this.echo(vo);
		} else {
			if (this.nextOperator != null)
				result = nextOperator.handleMessage(vo);
			else 
				result = "无法执行";
		}
		return result;
	}
	
	public void setNext(CommandName c) {
		this.nextOperator = c;
	}
	
	public abstract String getOperateParam();
	
	public abstract String echo(CommandVO vo);
}
