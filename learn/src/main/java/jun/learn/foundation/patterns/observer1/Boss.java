package jun.learn.foundation.patterns.observer1;

import java.util.ArrayList;
import java.util.List;

public class Boss {
	private Receptionist r;
	private List<ObServer> obServers = new ArrayList<ObServer>();
	
	public Boss(Receptionist r){
	
		this.r = r;
	}
	
	public void attach(ObServer obServer){
		
		obServers.add(obServer);
	}
	
	public void detach(ObServer obServer){
		
		obServers.remove(obServer);
	}
	public void beginOffice(){
		r.notifySomeOne();
	}
	
	public void intoOffice(){
		int i = 1;
		for(ObServer o : obServers){
			System.out.println("boss 发现了员工"+i+"在"+o.getState());
			i++;
		}
		
	}
}
