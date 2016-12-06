package jun.learn.foundation.ADTs;

public final class Complex {
	private final double re;
	private final double im;
	
	public Complex(double re,double im){
		this.re = re;
		this.im = im;
	}
	
	public double realPart(){
		return re;
	}
	
	public double imaginaryPart(){
		return im;
	}
	
	
}
