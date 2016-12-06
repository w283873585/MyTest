package learn.client.metro;

import java.util.ArrayList;
import java.util.List;

public class Station {
	
	public Station(String name) {
		this.name = name;
	}
	
	private String name;
	
	private List<Metro> mList = new ArrayList<Metro>();
	
	private List<Passenger> pList = new ArrayList<Passenger>();
	
	public void receive(Metro m) {
		mList.add(m);
	}
	
	public void receive(Passenger s) {
		pList.add(s);
	}
	
	public void release(Passenger s) {
		
	}
	
	public void release(Metro s) {
		mList.remove(s);
	}
	
	public String getName() {
		return this.name;
	}
	
	public static void main(String[] args) {
		Station station1 = new Station("站台1");
		Passenger xiaoming = new Passenger();
		xiaoming.reach(station1);
	}
}
