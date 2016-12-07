package jun.learn.scene.thread.client1;
class Pair { // Not thread-safe
	private int x, y;
	public Pair(int x, int y) {
		this.x = x;
		this.y = y;
	}
	public Pair() { this(0, 0); }
	public int getX() { return x; }
	public int getY() { return y; }
	public void incrementX() { x++; }
	public void incrementY() { y++; }
	public String toString() {
		return "x: " + x + ", y: " + y;
	}
	
	public class PairValuesNotEqualException extends RuntimeException {
		public PairValuesNotEqualException() {
			super("Pair values not equal: " + Pair.this);
		}
	}
	// Arbitrary invariant -- both variables must be equal:
	public void checkState() {
		if(x != y)
		throw new PairValuesNotEqualException();
	}
}