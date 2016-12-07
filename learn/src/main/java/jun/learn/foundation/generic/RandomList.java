package jun.learn.foundation.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RandomList<T> {
	private List<T> list = new ArrayList<T>();
	private Random rand = new Random(47);
	
	public T select() {
		return list.get(rand.nextInt(list.size()));
	}
}
