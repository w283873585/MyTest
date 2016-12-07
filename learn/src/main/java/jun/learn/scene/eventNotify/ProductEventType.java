package jun.learn.scene.eventNotify;

public enum ProductEventType {
	NEW_PRODUCT(1),
	DEL_PRODUCT(2),
	EDIT_PRODUCT(3),
	CLONE_PRODUCT(4);
	
	private int value = 0;
	private ProductEventType(int _value) {
		this.value = _value;
	}
	
	public int getValue() {
		return this.value;
	}
}
