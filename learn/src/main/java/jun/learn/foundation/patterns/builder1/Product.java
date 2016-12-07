package jun.learn.foundation.patterns.builder1;

import java.util.ArrayList;
import java.util.List;

public class Product {
	private List<String> part = new ArrayList<String>();  
    
	public void showProduct(){  
		
		System.out.println(part);
    }  
    
    public void add(String part){
    	
    	this.part.add(part);
    }
}
