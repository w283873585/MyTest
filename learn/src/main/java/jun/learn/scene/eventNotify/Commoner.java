package jun.learn.scene.eventNotify;

public class Commoner extends EventCustomer{

	public Commoner() {
		super.addCustomType(EventCustomType.NEW);
	}
	
	@Override
	public void exec(ProductEvent event) {
		Product p = event.getSource();
		
		ProductEventType type = event.getEventType();
		
		System.out.println("平民处理事件：" + p.getName() + "诞生，事件类型" + type);
	}
}
