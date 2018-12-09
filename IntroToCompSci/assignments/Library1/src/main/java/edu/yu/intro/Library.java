package	edu.yu.intro;

import java.util.*; 
import java.lang.IllegalArgumentException;


public class Library {
	private String name;
	private String address;
	private String phone;
	private ArrayList <Book> holdings = new ArrayList<>(10);

	/////Constructor
	public Library(String name, String address, String phone){
		if (name == null || name.replaceAll("\\s", "").toString().length() == 0 || address == null || address.replaceAll("\\s", "").toString().length() == 0 || phone == null || phone.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	///////////
	
	/////Getters
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phone;
	}
	//////////////////
	
	@Override
	public boolean equals(Object o) {
        if (o == this) {
        return true;
        }
        if (!(o instanceof Library)) {
            return false;
        }
        
        Library library = (Library) o;
        return Objects.equals(name, library.name) && Objects.equals(address, library.address) && Objects.equals(phone, library.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }
	
	
	/// Unique Methods
	public void add (Book b) {
		holdings.add(b);
	}
	
	public boolean isTitleInHoldings (String title) {
		if ((title == null) || (title.length() == 0)) {
			throw new IllegalArgumentException();
		}
		ArrayList <String> titles = new ArrayList<String>(10);
		for (int x = 0; x < holdings.size(); x++) {
			titles.add(holdings.get(x).getTitle()); 
		}
		
		if (titles.indexOf(title) >= 0) {
			return true;
		}
		else {
			return false;
		}
	}
	
	public boolean isISBNInHoldings (long isbn13) {
		ArrayList <Long> isbns = new ArrayList<>(10);
		for (int x = 0; x < holdings.size(); x++) {
			isbns.add(holdings.get(x).getISBN13()); 
		}
		if (isbns.indexOf(isbn13) >= 0) {
			return true;
		}
		else {
			return false;
		}
		
	}
	
	public Book getBook (long isbn13) {
		ArrayList <Long> isbns = new ArrayList<>(10);
		for (int x = 0; x < holdings.size(); x++) {
			isbns.add(holdings.get(x).getISBN13()); 
		}
		if (isbns.indexOf(isbn13) >= 0) {
			return holdings.get(isbns.indexOf(isbn13));
		}
		else {
			return null;
		}
	}
	
	public int nBooks() {
		return holdings.size();
	}



}