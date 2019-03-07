package	edu.yu.intro;

import java.util.*; 
import java.lang.IllegalArgumentException;


public class Library {
	private String name;
	private String address;
	private String phone;
	private List <Book> holdings = new ArrayList<>(10);

	//////Constructor
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
        if (o == null) {
        	return false;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        
        Library library = (Library) o;
        return Objects.equals(name, library.getName()); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }
    
    @Overidde
    public String toString() {
    	return String.format("%s %s %s", name, address, phone);
    }
	
	
	/// Unique Methods
	public void add (Book b) {
		holdings.add(b);
	}
	
	public boolean isTitleInHoldings (String title) {
		if ((title == null) || title.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		for (Book book : holdings) {
			if (book.getTitle().equals(title)) {
				return true;
			else {
				return false;
			}
		}
	}
	
	public boolean isISBNInHoldings (long isbn13) {
		if (((String.valueOf(isbn).replaceAll("\\s", "").toString().length()) != 13) || (isbn < 0)) {
			throw new IllegalArgumentException();
		}
		for (Book book : holdings) {
			if (book.getISBN13() == isbn13) {
				return true;
			else {
				return false;
			}
		}
		
	}
	
	public Book getBook (long isbn13) {
		if (((String.valueOf(isbn).replaceAll("\\s", "").toString().length()) != 13) || (isbn < 0)) {
			throw new IllegalArgumentException();
		}
		Book book1 = null;
		for (Book book : holdings) {
			if (book.getISBN13() == isbn13) {
				book1 = book;
			}
			else {
				book1 = null;
			}
		}
		return book1;
	}
	
	public int nBooks() {
		return holdings.size();
	}



}