package learn.client.client1;

import java.util.Enumeration;
import java.util.Vector;

public class Customer {
	private String _name;
	private Vector<Rental> _rentals = new Vector<Rental>();
	
	public Customer(String name){
		_name = name;
	}
	
	public void addRental(Rental rental){
		_rentals.addElement(rental);
	}
	
	public String getName(){
		return _name;
	}
	
	public String statement(){
		
		String result = "Rental Record for " + getName() + "\n";
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()){
			
			Rental each = rentals.nextElement();
			// show figures for this rental 
			result += "\t" + each.getMovie().getTitle() + "\t" + 
				String.valueOf(each.getCharge()) + "\n";
		}
		
		// add footer lines
		result += "Amount owed is " + String.valueOf(getTotalCharge()) + "\n";
		result += "You earned " + String.valueOf(getTotalFrequentRenterPoints()) +
			" frequent renter points";
		
		return result;
	}
	
	public String htmlStatement(){
		
		String result = "<customer>" + getName() + "</customer>\n";
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()){
			
			Rental each = rentals.nextElement();
			// show figures for this rental 
			result += "<rental>\n<title>" + each.getMovie().getTitle() + "</title>\n<price>" + 
				String.valueOf(each.getCharge()) + "</price>\n</rental>\n";
		}
		
		// add footer lines
		result += "<totalPrice>" + String.valueOf(getTotalCharge()) + "</totalPrice>\n";
		result += "<totalPoints>" + String.valueOf(getTotalFrequentRenterPoints()) +
			"</totalPoints>";
		
		return result;
	}
	
	private double getTotalCharge(){
		
		double result = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()){
			result += rentals.nextElement().getCharge();
		}
		return result;
		
	}
	
	private int getTotalFrequentRenterPoints(){
		
		int result = 0;
		Enumeration<Rental> rentals = _rentals.elements();
		while (rentals.hasMoreElements()){
			result += rentals.nextElement().getFrequentRenterPoints();
		}
		
		return result;
	}
	
	public static void main(String[] args) {
		
		Movie m1 = new Movie("hello", new Price_Childrens());
		Movie m2 = new Movie("hello1", new Price_Regular());
		Movie m3 = new Movie("hello2", new Price_NewRelease());
		Customer cust = new Customer("liyu");
		
		Rental rental1 = new Rental(m1, 4);
		Rental rental2 = new Rental(m2, 4);
		Rental rental3 = new Rental(m3, 4);
		
		cust.addRental(rental1);
		cust.addRental(rental2);
		cust.addRental(rental3);
		
		System.out.println(cust.statement() + "\n");
		System.out.println(cust.htmlStatement());
	}
	
	
}
