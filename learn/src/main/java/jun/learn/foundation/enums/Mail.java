package jun.learn.foundation.enums;

import java.util.Iterator;

// 责任链处理信�?
public class Mail {
	enum GeneralDelivery{YES, NO1, NO2, NO3, NO4, NO5}
	enum Scannability{UNSCANNABLE, YES1, YES2, YES3, YES4}
	enum Readability{ILLEGIBLE, OK1, OK2, OK3, OK4, OK5, OK6}
	enum Adress{INCORRECT, OK1, OK2, OK3, OK4, OK5, OK6}
	enum ReturnAddress{MISSING, OK1, OK2, OK3, OK4, OK5}
	
	GeneralDelivery generalDelivery;
	Scannability scannability;
	Readability readability;
	Adress adress;
	ReturnAddress returnAddress;
	
	static long counter = 0;
	long id = counter++;
	public String toString() {return "Mail " + id;}
	
	public String details() {
		return toString() + " "
				+ generalDelivery + ","
				+ scannability + ","
				+ readability + ","
				+ adress + ","
				+ returnAddress + ",";
	}
	
	public static Mail randomMail() {
		Mail m = new Mail();
		m.generalDelivery = Enums.random(GeneralDelivery.class);
		m.scannability = Enums.random(Scannability.class);
		m.readability = Enums.random(Readability.class);
		m.adress = Enums.random(Adress.class);
		m.returnAddress = Enums.random(ReturnAddress.class);
		return m;
	}
	
	public static Iterable<Mail> generator(final int count) {
		return new Iterable<Mail>() {
			int n = count;
			public Iterator<Mail> iterator() {
				return new Iterator<Mail>() {
					public boolean hasNext() { return n-- > 0; }
					public Mail next() { return randomMail(); }
					public void remove() { throw new UnsupportedOperationException(); }
				};
			}
		};
	}
	
}
