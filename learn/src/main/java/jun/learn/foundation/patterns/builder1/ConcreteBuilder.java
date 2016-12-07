package jun.learn.foundation.patterns.builder1;

class ConcreteBuilder extends Builder {  
    private Product product = new Product();  
      
    public Product getProduct() {  
        return product;  
    }  

	@Override
	public void setPartA() {
		
		product.add("partA");
	}

	@Override
	public void setPartB() {
		
		product.add("partB");
		
	}

	@Override
	public void setPartC() {
		
		product.add("partC");
		
	}  
} 