package jun.learn.scene.eventNotify;

public class Product implements Cloneable{

	private String name;
	
	private boolean canChanged = false;
	
	public Product(ProductManager manager, String _name) {
		if (manager.isCreateProduct()) {
			canChanged = true;
			this.name = _name;
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		if (canChanged)
			this.name = name;
	}

	public Product clone() {
		Product p = null;
		try {
			p = (Product) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return p;
	}
}
