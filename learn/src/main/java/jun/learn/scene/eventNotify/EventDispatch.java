package jun.learn.scene.eventNotify;

import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

public class EventDispatch implements Observer{

	private static final EventDispatch dispatch = new EventDispatch();
	
	private Vector<EventCustomer> customer = new Vector<EventCustomer>();
	
	private EventDispatch() {}
	
	public static EventDispatch getEventDispathc() {
		return dispatch;
	}
	
	@Override
	public void update(Observable o, Object arg) {
		Product p = (Product) arg;
		
		ProductEvent event = (ProductEvent) o;
		// 处理者处理， 这里是中介者模式的核心， 可以是很复杂的业务逻辑
		for (EventCustomer e : customer) {
			for (EventCustomType t : e.getEventCustomType()) {
				if (t.getValue() == event.getEventType().getValue())
					e.exec(event);
			}
		}
	}
	
	public void registerCustomer(EventCustomer customer) {
		this.customer.add(customer);
	}
}
