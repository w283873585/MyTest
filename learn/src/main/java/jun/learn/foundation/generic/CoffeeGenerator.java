package jun.learn.foundation.generic;

import java.util.Iterator;
import java.util.Random;

import jun.learn.foundation.generic.Coffee.Americano;
import jun.learn.foundation.generic.Coffee.Breve;
import jun.learn.foundation.generic.Coffee.Cappuccino;
import jun.learn.foundation.generic.Coffee.Latte;
import jun.learn.foundation.generic.Coffee.Mocha;

public class CoffeeGenerator implements Generator<Coffee>, Iterable<Coffee>{
	
	private Class<?>[] types = {
		Latte.class,
		Breve.class,
		Mocha.class,
		Americano.class,
		Cappuccino.class,
	};
	
	public CoffeeGenerator(int n) {
		this.size = n;
	}
	
	private int size = 0;
	private static Random rand = new Random(47);
	
	@Override
	public Coffee next() {
		try {
			return (Coffee)types[rand.nextInt(types.length)].newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}

	
	private class CoffeeIterator implements Iterator<Coffee>{
		int count = size;
		@Override
		public boolean hasNext() {
			return count > 0;
		}

		@Override
		public Coffee next() {
			count--;
			return CoffeeGenerator.this.next();
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException();
		}
		
	}

	@Override
	public Iterator<Coffee> iterator() {
		return new CoffeeIterator();
	}
	
	
	public static void main(String[] args) {
		CoffeeGenerator gen = new CoffeeGenerator(11);
		for (Coffee co : new CoffeeGenerator(11)) {
			System.out.println(co.toString());
		}
	}
}
