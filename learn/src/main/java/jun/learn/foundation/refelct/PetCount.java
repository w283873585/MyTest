package jun.learn.foundation.refelct;

import java.util.HashMap;

public class PetCount{
	
	@SuppressWarnings("serial")
	static class PetCounter extends HashMap<String,Integer>{
		
		public void count(String type){
			Integer quantity = get(type);
			quantity = quantity==null ? 1 : quantity+1;
			put(type, quantity);
		}
	}
	
	public static void countPets(PetCreator creator){
		PetCounter counter = new PetCounter();
		for(Pet pet : creator.createArray(20)){
			System.out.println(pet.getClass().getSimpleName() + " ");
			if(pet instanceof  Pet){
				counter.count("Pet");
			}
			if(pet instanceof  Dog){
				counter.count("Dog");
			}
			if(pet instanceof  Dog2){
				counter.count("Dog2");
			}
			if(pet instanceof  Dog3){
				counter.count("Dog3");
			}
			if(pet instanceof  Cat){
				counter.count("Cat");
			}
			if(pet instanceof  Cat2){
				counter.count("Cat2");
			}
		}
		System.out.println(counter);
	}
	
	public static void main(String[] args) {
		countPets(new ForNameCreator());
	}
}
