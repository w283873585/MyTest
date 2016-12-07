package jun.learn.foundation.patterns.state1;

public class MorningState implements State{

	public void startWork(Worker w) {
		if(w.time < 12){
			System.out.println("早上，精神百倍，做事很速率");
		}else{
			w.current = new NoonState();
			w.startWork();
		}
		
	}

}
