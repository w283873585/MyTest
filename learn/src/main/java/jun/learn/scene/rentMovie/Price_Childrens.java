package jun.learn.scene.rentMovie;

public class Price_Childrens extends Price{

	@Override
	public double getCharge(int daysRented) {
		double result = 2;
		if(daysRented > 3){
			result += (daysRented - 3) * 1.5;
		}
		return result;
	}

}
