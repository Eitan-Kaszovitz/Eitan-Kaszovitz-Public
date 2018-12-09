package	edu.yu.intro;

import java.util.*; 
import java.lang.IllegalArgumentException;


public class Book {
	private String title;
	private String singleAuthor;
	private long isbn;
	private String bookType;

	
	public Book(String title, String singleAuthor, long isbn, String bookType){
		if (title == null || title.replaceAll("\\s", "").toString().length() == 0 || singleAuthor == null || singleAuthor.replaceAll("\\s", "").toString().length() == 0 || bookType == null || bookType.replaceAll("\\s", "").toString().length() == 0) {
			throw new IllegalArgumentException();
		}
		/*if ((String.valueOf(isbn).length()) != 14) {
			throw new IllegalArgumentException();
		}*/
		
		if (!bookType.equals("hardcover") && !bookType.equals("paperback") && !bookType.equals("ebook")) {
			throw new IllegalArgumentException();
		}
		this.title = title;
		this.singleAuthor = singleAuthor;
		this.isbn = isbn;
		this.bookType = bookType;
	}
	
	public String getAuthor() {
		return singleAuthor;
	}
	
	public String getTitle() {
		return title;
	}
	
	public long getISBN13() {
		return isbn;
	}
	
	public String getBookType() {
		return bookType;
	}

    @Override
	public boolean equals(Object o) {
        if (o == this) {
        return true;
        }
        if (!(o instanceof Book)) {
            return false;
        }
        
        Book book = (Book) o;
        return (isbn == book.isbn) && Objects.equals(title, book.title) && Objects.equals(singleAuthor, book.singleAuthor) && Objects.equals(bookType, book.bookType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, singleAuthor, isbn, bookType);
    }

}