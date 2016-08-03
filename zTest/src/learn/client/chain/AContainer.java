package learn.client.chain;

public class AContainer implements Pipeline{

	PipelineSupport pipeline = new PipelineSupport();
	
	@Override
	public Valve getBasic() {
		return pipeline.getBasic();
	}

	@Override
	public void setBasic(Valve basic) {
		pipeline.setBasic(basic);
	}

	@Override
	public void addValve(Valve valve) {
		pipeline.addValve(valve);
	}

	@Override
	public Valve[] getValves() {
		return pipeline.getValves();
	}

	@Override
	public void invoke() {
		pipeline.invoke();
	}

	@Override
	public void removeValve(Valve valve) {
		pipeline.removeValve(valve);
	}
}
