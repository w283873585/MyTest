package jun.learn.foundation.patterns.observer1;

public class People1 implements ObServer{
	public int state;
	public void update() {
		state = 1;
		System.out.println("boss come on!");
	}
	public String getState() {
		
		if(state==1){
			return "认真工作";
		}
		return "看电影";
	}

}
