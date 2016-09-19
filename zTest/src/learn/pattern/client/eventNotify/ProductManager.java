package learn.pattern.client.eventNotify;

public class ProductManager {
	private boolean isPermittedCreate = false;
	
	public Product createProduct(String name) {
		isPermittedCreate = true;
		Product p = new Product(this, name);
		new ProductEvent(p, ProductEventType.NEW_PRODUCT);
		return p;
	}
	
	public void abandonProduct(Product p) {
		new ProductEvent(p, ProductEventType.DEL_PRODUCT);
		p = null;
	}
	
	public void editProduct(Product p, String name) {
		p.setName(name);
		new ProductEvent(p, ProductEventType.EDIT_PRODUCT);
	}
	

	public boolean isCreateProduct() {
		return isPermittedCreate;
	}
	
	public Product clone(Product p) {
		new ProductEvent(p, ProductEventType.CLONE_PRODUCT);
		return p.clone();
	}

}
