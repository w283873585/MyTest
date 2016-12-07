package jun.learn.foundation.patterns.builder1;

public class Director {  
    private Builder builder; 
    public Product getAProduct(){
    	builder = new ConcreteBuilder();
        builder.setPartA();
        builder.setPartB();
        builder.setPartC();
        return builder.getProduct();  
    }  
    public Product getBProduct(){  
    	builder = new ConcreteBuilder();
    	builder.setPartA();
        builder.setPartC(); 
        return builder.getProduct();  
    }  
    
    
    public static void main(String[] args) {
    	 Director director = new Director();  
         Product product1 = director.getAProduct();  
         product1.showProduct();  
   
         Product product2 = director.getBProduct();  
         product2.showProduct();
	}
}  