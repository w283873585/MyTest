package jun.learn.scene.metro;

public class Metro {
	private String name;
	
	private Station currentStation;
	
	public Metro(String name) {
		this.name = name;
	}
	
	public void rearch(Station s) {
		this.currentStation = s;
		this.currentStation.receive(this);
	}
	
	public Station getCurrentStation() {
		return currentStation;
	}

	public void run() {
		System.out.println("i am running");
	}
	
	public String getName() {
		return name;
	}
	
	public static void main(String[] args) {
		Station station1 = new Station("站台1");
		
		Metro metro = new Metro("1号线");
		
		Passenger xiaoming = new Passenger();
		
		xiaoming.reach(station1);
	}
}
