package jun.learn.foundation.patterns.visitor1;

public abstract class Traitor {
	private String name;
	private int age;
	
	public Traitor(int age, String name) {
		this.age = age;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getAge() {
		return this.age;
	}
	
	public abstract void accpet(Visitor visitor);
	public abstract boolean targetSleep();
	public abstract int getSoliderCount();
}
