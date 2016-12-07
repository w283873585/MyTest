package jun.learn.foundation.generic;

public class Teller {
	private static long counter = 1;
	private final long id = counter++;
	private Teller() {}
	public static Generator<Teller> generator = new Generator<Teller>() {
		
		@Override
		public Teller next() {
			// TODO Auto-generated method stub
			return null;
		}
	};
}
