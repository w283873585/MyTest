package jun.learn.foundation.patterns.state1;

public class Worker {
	
	public State current;
	public int time;
	
	public Worker(){
		time = 9;
		current = new MorningState();
	}
	
	public void startWork(){

		current.startWork(this);
	}
	
	
	public static void main(String[] args) {
		Worker w = new Worker();
		w.startWork();
		w.time = 14;
		w.startWork();
	}
}
