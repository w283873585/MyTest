package jun.learn.scene.eventNotify;

public enum EventCustomType {
	NEW(1),
	DEL(2),
	EDIT(3),
	CLONE(4);
	
	private int value;
	
	private EventCustomType(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return this.value;
	}
}
