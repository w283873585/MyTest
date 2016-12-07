package jun.learn.foundation.patterns.builder2;

public class Director {
	private Builder builder;
	
	public Director(Builder builder) {
		this.builder = builder;
	}
	
	public Product getProductAB(){
		builder.setPartA();
		builder.setPartB();
		return builder.getProduct();
	}
	
	public Product getProductABC(){
		builder.setPartA();
		builder.setPartB();
		builder.setPartC();
		return builder.getProduct();
	}
}
