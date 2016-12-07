package jun.learn.foundation.patterns.mediator1;

public class Mediator {
	private Boy boy; 
	private Girl girl; 
	
	public Mediator(Boy boy,Girl girl){
		
		this.boy = boy;
		this.girl = girl;
	}
	
	public void notifyBoy(){
		String str = "逛街购物";
		boy.doSomething(str);
	}
	
	public void notifyGirl(){
		String str = "看电影";
		girl.doSomething(str);
	}
}
