package jun.spring;

import java.util.ArrayList;
import java.util.List;

public class Station {
	
	private String name;
	
	private List<Metro> mList = new ArrayList<Metro>();
	
	private List<Passenger> pList = new ArrayList<Passenger>();
	
	public void accessM(Metro m) {
		mList.add(m);
	}
	
	public void access(Passenger s) {
		pList.add(s);
	}
	
	public String getName() {
		return this.name;
	}
}
