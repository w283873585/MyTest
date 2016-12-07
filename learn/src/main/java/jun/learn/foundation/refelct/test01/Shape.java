package jun.learn.foundation.refelct.test01;

public class Shape {
	void draw(){
		System.out.println(this + ".draw()");
	}
	public Shape(int i){
		
	}
	
	public String toString(){
		return this.getClass().getSimpleName();
	}
	
	public static void main(String[] args) throws Exception {
		Shape shape = new Circle(1,true);
		Shape shape_t = Shape.class.newInstance();
		shape.draw();
		shape_t.draw();
		
	}
}
