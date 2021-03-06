package edu.yu.intro;

import java.lang.*;
import java.util.*;

public class Patron {

	private String firstName;
	private String lastName;
	private String address;
	private UUID id;
	private List <Book> booksOut = new ArrayList<>(); 
///////////////////

	public Patron (String firstName, String lastName, String address){
		if (firstName == null || firstName.replaceAll("\\s", "").toString().length() == 0 || lastName == null || lastName.replaceAll("\\s", "").toString().length() == 0 || address == null || address.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		this.firstName = firstName;
		this.lastName = lastName;
		this.address = address;
		id = UUID.randomUUID();
	}
//////////////////

	public String getFirstName () {
		return firstName;
	}
	
	public String getLastName () {
		return lastName;
	}
	
	public String getAddress () {
		return address;
	}
	
	public String getId () {
		return id.toString();
	}
	public List <Book> getBooksOut () {
		return booksOut;
	}
////////////////

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
        
        Patron patron = (Patron) o;
        return Objects.equals(id.toString(), patron.getId()); 
    }

    @Override
    public int hashCode() {
        return Objects.hash(id.toString());
    }
	
	@Override
    public String toString() {
    	return String.format("%s %s %s %s", firstName, lastName, address, id.toString());
    }
////////////////

	public void addBook (Book book) {
		booksOut.add(book);
	}


} 