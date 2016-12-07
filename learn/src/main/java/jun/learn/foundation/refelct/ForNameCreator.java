package jun.learn.foundation.refelct;

import java.util.ArrayList;
import java.util.List;


public  class ForNameCreator extends PetCreator{

	private static List<Class<? extends Pet>> types = new ArrayList<Class<? extends Pet>>();
	
	private static String[] typeNames = {
			"com.my.refelct.Dog",
			"com.my.refelct.Dog2",
			"com.my.refelct.Dog3",
			"com.my.refelct.Cat",
			"com.my.refelct.Cat2" 
	};
	
	@SuppressWarnings("unchecked")
	private static void loader(){
		try {
			for(String name : typeNames){
				types.add(
					(Class<? extends Pet>)Class.forName(name));
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	static{
		loader();
	}
	
	@Override
	public List<Class<? extends Pet>> types() {
		return types;
	}
	
}
