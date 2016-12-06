package learn.client.metro;

import java.util.ArrayList;
import java.util.List;

public class MetroLine {
	
	private int index = 0;
	
	private List<Station> stations = new ArrayList<Station>();

	public void add(Station s) {
		stations.add(s);
	}
}
