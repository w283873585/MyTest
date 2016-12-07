package jun.learn.foundation.refelct.test02;

import java.util.Random;


public class ClassInitialization{

	public static Random rand = new Random(47);
	
	public static void main(String[] args) {
		Class<Initable> initable = Initable.class;
		System.out.println(Initable.staticFinal);
		//System.out.println(Initable.staticFinal2);
		
	}
}
