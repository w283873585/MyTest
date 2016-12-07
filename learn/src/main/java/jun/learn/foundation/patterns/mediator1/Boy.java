package jun.learn.foundation.patterns.mediator1;

import java.util.Random;

public class Boy {
	
	
	public void goMovie(Mediator mediator){
		String str = "看电影";
		System.out.println("男孩想去"+str);
		mediator.notifyGirl();
	}
	
	public void doSomething(String str){
		String s1 = "男孩也想去";
		String s2 = "男孩不想去";
		Random r = new Random();
		int i = r.nextInt(2);
		if(i==1){
			System.out.println(s1+str);
		}else{
			System.out.println(s2+str);
		}
	}
}
