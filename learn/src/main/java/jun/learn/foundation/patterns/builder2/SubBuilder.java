package jun.learn.foundation.patterns.builder2;

public class SubBuilder implements Builder{
	
	private Product product;
	
	public SubBuilder() {
		product = new Product();
	}
	
	
	@Override
	public Product getProduct() {
		
		return product;
	}


	@Override
	public void setPartA() {
		product.add("hello");
	}


	@Override
	public void setPartB() {
		product.add("world");
	}


	@Override
	public void setPartC() {
		product.add("for anyone");
	}


	@Override
	public void clear() {
		product.clear();
	}

}
