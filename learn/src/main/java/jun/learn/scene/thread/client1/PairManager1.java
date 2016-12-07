package jun.learn.scene.thread.client1;
class PairManager1 extends PairManager {
	public synchronized void increment() {
		p.incrementX();
		p.incrementY();
		store(getPair());
	}
}