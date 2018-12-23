package	edu.yu.intro;

import java.util.*; 
import java.lang.IllegalArgumentException;

public class Library {
	private String name;
	private String address;
	private String phone;
	private List <Book> holdings = new ArrayList<>();
	private Set<Patron> patronList = new HashSet<>();
////////////

	public Library(String name, String address, String phone){
		if (name == null || name.replaceAll("\\s", "").toString().length() == 0 || address == null || address.replaceAll("\\s", "").toString().length() == 0 || phone == null || phone.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
///////////
	
	public String getName() {
		return name;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getPhoneNumber() {
		return phone;
	}
	
	public Set<Patron> getList() {
		return patronList;
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
    
    @Override
    public String toString() {
    	return String.format("%s %s %s", name, address, phone);
    }
///////////////////
	
	public void borrow(Patron patron, Book book) {
		if (!patronList.contains(patron)) {
			throw new IllegalArgumentException();
		}
		if (!holdings.contains(book)) {
			throw new IllegalArgumentException();
		}
		patron.addBook(book);	
	}
	
	public Collection <Book> onLoan(Patron patron) {
		if (!patronList.contains(patron)) {
			throw new IllegalArgumentException();
		}
		return patron.getBooksOut();
	}
	
	public Collection <Book> search (BookFilter filter) {
		List <Book> filteredHoldings = new ArrayList<>();
		for (Book book : holdings) {
			if (filter.filter(book)) {
				filteredHoldings.add(book);
			}
		}
		return filteredHoldings;
	} 
////////////////////////
	
	public void add(Book b) {
		holdings.add(b);
	}
	
	public void add(Patron patron) {
		patronList.add(patron);
	}
	
	public Patron get(String uuid) {
		Patron patron1 = null;
		if (uuid == null || uuid.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		for (Patron patron : patronList) {
			if (patron.getId().equals(uuid)) {
				patron1 = patron;
			}
			else {
				patron1 = null;
			}
		}
		return patron1;
	}
	
	public Set<Patron> byLastNamePrefix(final String prefix) {
		if (prefix == null) {
			throw new IllegalArgumentException();
		}
		if (prefix.replaceAll("\\s", "").toString().length() == 0) {
			return patronList;
		}
		Set<Patron> patronNewList = new HashSet<>();
		List<Patron> patronArrayList = new ArrayList<>(patronList);
		for (Patron patron : patronArrayList) {
			if (patron.getLastName().startsWith(prefix)) {
				patronNewList.add(patron);
			}
		}
		System.out.println(patronNewList);
		return patronNewList;
	}
	
	public int nPatrons() {
		return patronList.size();
	}
///////////////////////////
	
	public int nBooks() {
		return holdings.size();
	}
	
	public void clearBooks() {
		holdings.clear();
	}
	public void clearPatrons() {
		patronList.clear();
	}
	
}
