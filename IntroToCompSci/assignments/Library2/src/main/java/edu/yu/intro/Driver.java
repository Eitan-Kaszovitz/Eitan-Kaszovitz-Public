package edu.yu.intro;

public class Driver {

	public static void run() {
		Library pplLibraryclear = new Library ("PPL Library", "500 Central Avenue", "5169675973");
		pplLibraryclear.clear();
		Patrons.Singleton.clear();
		
		Library pplLibrary = new Library ("PPL Library", "500 Central Avenue", "5169675973");
		Book book1 = new Book ("Moneyball", "Michael Lewis", 5121211212121L, "paperback");
		Book book2 = new Book ("Hamlet", "Shakespeare", 3121214312121L, "paperback");
		Book book3 = new Book ("Romeo and Juliet", "Shakespeare", 3121103321321L, "ebook");
		
		pplLibrary.add(book1);
		pplLibrary.add(book2);
		pplLibrary.add(book3);
		
		Patron reuven = new Patron ("Reuven", "Schwartz", "123 Anywhere Street");
		Patron shimon = new Patron ("Shimon", "Sanchez", "456 Nowhere Street");
		
		Patrons.Singleton.add(reuven);
		Patrons.Singleton.add(shimon);
	}
	
}