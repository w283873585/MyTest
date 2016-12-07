package jun.learn.foundation.patterns.state1;

public class AfterNoonState implements State{

	public void startWork(Worker w) {
		if(w.time<18){
			System.out.println("下午快下班，真想回家");
		}else{
			System.out.println("已经下班了。。。");
		}
		
	}

}
