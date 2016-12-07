package jun.learn.foundation.patterns.observer1;

public class MainE {
	public static void main(String[] args) {
		
		People1 p1 = new People1();
		People1 p2 = new People1();
		People1 p3 = new People1();
		People1 p4 = new People1();
		People1 p5 = new People1();
		Receptionist r = new Receptionist();
		r.attach(p1);
		r.attach(p2);
		r.attach(p3);
		r.attach(p4);
		Boss boss = new Boss(r);
		boss.attach(p1);
		boss.attach(p2);
		boss.attach(p3);
		boss.attach(p4);
		boss.attach(p5);
		boss.beginOffice();
		boss.intoOffice();
		
		
	}
}
