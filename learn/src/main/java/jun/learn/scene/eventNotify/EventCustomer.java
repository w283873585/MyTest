package jun.learn.scene.eventNotify;

import java.util.Vector;

public abstract class EventCustomer {
	
	private Vector<EventCustomType> customType = new Vector<EventCustomType>();
	
	public void addCustomType(EventCustomType type) {
		customType.add(type);
	}
	
	public Vector<EventCustomType> getEventCustomType() {
		return this.customType;
	}
	
	public abstract void exec(ProductEvent event);
}
