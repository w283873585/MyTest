package jun.learn.scene.cardSystem;

public enum StrategyMan {

	SteadyDeduction("learn.pattern.client.cardSystem.SteadyDeduction"),
	FreeDeduction("learn.pattern.client.cardSystem.FreeDeduction");
	
	private String value;
	private StrategyMan(String _value) {
		this.value = _value;
	}
	
	public String getValue() {
		return this.value;
	}
}
