package learn.pattern.client.eventNotify;

public class Nobleman extends EventCustomer{

	public Nobleman() {
		super.addCustomType(EventCustomType.CLONE);
		super.addCustomType(EventCustomType.EDIT);
	}
	
	@Override
	public void exec(ProductEvent event) {
		Product p = event.getSource();
		
		ProductEventType type = event.getEventType();
		if (type.getValue() == EventCustomType.CLONE.getValue())
			System.out.println("贵族处理事件：" + p.getName() + "克隆，事件类型" + type);
		if (type.getValue() == EventCustomType.EDIT.getValue())
			System.out.println("贵族处理事件：" + p.getName() + "修改，事件类型" + type);
	}
}
