package jun.learn.foundation.thread.test;

public class Joiner extends Thread{
	private Sleeper sleeper;
	public Joiner(String name, Sleeper sleeper) {
		super(name);
		this.sleeper = sleeper;
		start();
	}
	
	public void run() {
		try {
			sleeper.join();
		} catch (InterruptedException e) {
			System.out.println("interrupted");
		}
		System.out.println(getName() + "join completed");
	}
	
	public static void main(String[] args) {
		Sleeper sleepy = new Sleeper("sleepy", 1500),
				grumpy = new Sleeper("grumpy", 1500);
		
		Joiner dopey = new Joiner("Dopey", sleepy),
			   doc = new Joiner("Doc", grumpy);
		
		grumpy.interrupt();
		
		/*
		 grumpywas interrupted. isInterrupted() false
		 grumpyhas awakened
		 Docjoin completed
		 sleepyhas awakened
		 Dopeyjoin completed
		*/
	}
}
