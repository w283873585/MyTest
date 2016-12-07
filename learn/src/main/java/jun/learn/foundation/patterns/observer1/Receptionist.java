package jun.learn.foundation.patterns.observer1;

import java.util.ArrayList;
import java.util.List;

public class Receptionist implements Subject{
	private List<ObServer> obServers = new ArrayList<ObServer>();
	
	public void attach(ObServer obServer){
		
		obServers.add(obServer);
	}
	
	public void detach(ObServer obServer){
		
		obServers.remove(obServer);
	}
	
	public void notifySomeOne(){
		
		for(ObServer i : obServers){
			i.update();
		}
	}
}
