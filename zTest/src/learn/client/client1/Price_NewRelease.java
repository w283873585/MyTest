package learn.client.client1;

public class Price_NewRelease extends Price{

	@Override
	public double getCharge(int daysRented) {
		
		return daysRented * 3;
	}
	
	@Override
	public int getFrequentRenterPoints(int daysRented){
		
		return (daysRented > 1) ? 2 : 1;
	}
}
