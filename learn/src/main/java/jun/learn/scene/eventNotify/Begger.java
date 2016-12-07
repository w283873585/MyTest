package jun.learn.scene.eventNotify;

public class Begger extends EventCustomer{

	public Begger() {
		super.addCustomType(EventCustomType.DEL);
	}
	
	@Override
	public void exec(ProductEvent event) {
		Product p = event.getSource();
		
		ProductEventType type = event.getEventType();
		
		System.out.println("乞丐处理事件：" + p.getName() + "销毁，事件类型" + type);
	}

}
