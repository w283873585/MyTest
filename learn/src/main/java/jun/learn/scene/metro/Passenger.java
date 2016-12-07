package jun.learn.scene.metro;

public class Passenger {
	
	private Station originStation;
	
	public void reach(Station s) {
		this.originStation = s;
	}
	
	public void leave(Station s) {
		s.release(this);
	}
	
	public Station getOriginStation() {
		return originStation;
	}
}
