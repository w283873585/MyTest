package jun.learn.foundation.patterns.builder2;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private List<String> interComponent = new ArrayList<String>();
	public void add(String str){
		interComponent.add(str);
	}
	
	public void clear() {
		interComponent.clear();
	}
}
