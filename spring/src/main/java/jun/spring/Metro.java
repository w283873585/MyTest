package jun.spring;

import java.util.ArrayList;
import java.util.List;

public class Metro {
	private List<Passenger> container = new ArrayList<Passenger>();
	private MetroStatus status = MetroStatus.suspend;
	
	public void run() {
		System.out.println("i am running");
	}
	
	public void suspend() {
		status = MetroStatus.suspend;
	}
	
	public boolean access(Passenger p) {
		if (status != MetroStatus.suspend) {
			return false;
		}
		container.add(p);
		return true;
	}
	
	public enum MetroStatus{
		getIn,
		getOut,
		running,
		suspend;
	}
}
