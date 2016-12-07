package jun.learn.foundation.patterns.mediator1;

import java.util.Random;

public class Girl {
	
	public void goShopping(Mediator mediator){
		String str = "购物";
		System.out.println("女孩想去"+str);
		mediator.notifyBoy();
	}
	
	public void doSomething(String str){
		String s1 = "女孩也想去";
		String s2 = "女孩不想去";
		Random r = new Random();
		int i = r.nextInt(2);
		if(i==1){
			System.out.println(s1+str);
		}else{
			System.out.println(s2+str);
		}
	}
}
