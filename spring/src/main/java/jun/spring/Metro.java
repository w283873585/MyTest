package jun.spring;

import java.util.ArrayList;
import java.util.List;

public class Metro {
	private String name;
	
	private MetroStatus status = MetroStatus.suspend;
	
	private List<Passenger> container = new ArrayList<Passenger>();
	
	private MetroLine line = new MetroLine();
	
	public Metro(String name) {
		this.name = name;
	}
	
	public void run() {
		status = MetroStatus.running;
		System.out.println("i am running");
	}
	
	public boolean access(Passenger p) {
		if (status != MetroStatus.suspend) {
			return false;
		}
		container.add(p);
		return true;
	}
	
	public void rearch(Station s) {
		status = MetroStatus.suspend;
		s.accessM(this);
	}
	
	public String getName() {
		return name;
	}
	
	public enum MetroStatus{
		getIn,
		getOut,
		running,
		suspend;
	}
}
