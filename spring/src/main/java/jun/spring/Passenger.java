package jun.spring;

public class Passenger {

	public void reach(Station s) {
		s.access(this);
	}
}
