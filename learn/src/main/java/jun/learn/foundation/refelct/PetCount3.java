package jun.learn.foundation.refelct;

import java.util.LinkedHashMap;
import java.util.Map;


public class PetCount3{
	
	@SuppressWarnings("serial")
	static class PetCounter extends LinkedHashMap<Class<? extends Pet>, Integer>{
		
		public PetCounter(){
			put(Dog.class, 0);
			put(Dog2.class, 0);
			put(Dog3.class, 0);
			put(Cat.class, 0);
			put(Cat2.class, 0);
		}
		
		public void count(Pet pet){
			for(Map.Entry<Class<? extends Pet>,Integer> pair : entrySet()){
				if(pair.getKey().isInstance(pet)){
					put(pair.getKey(),pair.getValue()+1);
				}
			}
		}
		
	}
	
	public static void main(String[] args) {
		PetCounter petCount = new PetCounter();
		for(Pet pet : Pets.createArray(20)){
			petCount.count(pet);
		}
		System.out.println(petCount);
	}
	
}
