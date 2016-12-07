package jun.learn.foundation.refelct;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public  class LiteralPetCreator extends PetCreator{

	
	private static final List<Class<? extends Pet>> allTypes = 
			Collections.unmodifiableList(Arrays.asList(
					Dog.class,
					Dog2.class,
					Dog3.class,
					Cat.class,
					Cat2.class));
	private static final List<Class<? extends Pet>> types = allTypes.subList(
			allTypes.indexOf(Dog.class), allTypes.size());
	
	@Override
	public List<Class<? extends Pet>> types() {
		return types;
	}
	
	public static void main(String[] args) {
		System.out.println(new LiteralPetCreator().types());
	}
	
}
