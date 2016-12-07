package jun.learn.foundation.patterns.mediator1;

public class Client {
	public static void main(String[] args) {
		Boy boy = new Boy();
		Girl girl = new Girl();
		Mediator m = new Mediator(boy,girl);
		boy.goMovie(m);
		girl.goShopping(m);
		
	}
}
