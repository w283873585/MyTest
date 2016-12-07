package jun.learn.foundation.patterns.state1;

public class NoonState implements State{

	public void startWork(Worker w) {
		if(w.time < 14){
			System.out.println("zhongwu，精神百倍，做事很速率");
		}else{
			w.current = new AfterNoonState();
			w.startWork();
		}
		
	}

}
