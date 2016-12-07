package jun.learn.foundation.generic;

public class BasicGenerator<T> implements Generator<T>{
	private Class<T> type;
	
	public BasicGenerator(Class<T> type) {
		this.type = type;
	}
	
	@Override
	public T next() {
		try {
			return type.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			throw new RuntimeException();
		}
	}
	
	public static <T> Generator<T> create(Class<T> type) {
		return new BasicGenerator<>(type);
	}

	
	
	public static void main(String[] args) {
		
		Generator<Coffee> creator = create(Coffee.class);
		for (int i = 0; i < 10; i++) {
			System.out.println(creator.next());
		}
	}
}
