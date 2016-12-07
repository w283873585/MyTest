package jun.learn.scene.thread.client1;
// Use a critical section:
class PairManager2 extends PairManager {
	public void increment() {
		Pair temp;
		synchronized(this) {
			p.incrementX();
			p.incrementY();
			temp = getPair();
		}
		store(temp);
	}
}