package edu.yu.intro;

import java.util.*; 
import java.lang.*; 
import java.lang.IllegalArgumentException;

public class BookFilter {
	private String title;
	private String singleAuthor;
	private long isbn;
	private String bookType;
	
	private BookFilter (Builder builder) {
		this.title = builder.title;
		this.singleAuthor = builder.singleAuthor;
		this.isbn = builder.isbn;
		this.bookType = builder.bookType;
	}
	
	boolean titleStatus;
	boolean authorStatus;
	boolean isbnStatus;
	boolean bookTypeStatus;
	public boolean filter (Book book) {	
		titleStatus = false;
		authorStatus = false;
		isbnStatus = false;
		bookTypeStatus = false;
		
		if (Objects.equals(this.title, null)) {
			titleStatus = true;
		}
		if (Objects.equals(this.singleAuthor, null)) {
			authorStatus = true;
		}
		if (this.isbn == 0) {
			isbnStatus = true;
		}
		if (Objects.equals(this.bookType, null)) {
			bookTypeStatus = true;
		}
		
		if (Objects.equals(this.title, book.getTitle())) {
			titleStatus = true;
		}
		if (Objects.equals(this.singleAuthor, book.getAuthor())) {
			authorStatus = true;
		}
		if (this.isbn == book.getISBN13()) {
			isbnStatus = true;
		}
		if (Objects.equals(this.bookType, book.getBookType())) {
			bookTypeStatus = true;
		}
		
		if (titleStatus && authorStatus && isbnStatus && bookTypeStatus) {
			return true;
		}
		else {
			return false;
		}
	}
	/////////nested class
	public static class Builder {
		private String title;
		private String singleAuthor;
		private long isbn;
		private String bookType;
		
		public Builder () {
		}
		public Builder setAuthor (String author) {
			if (author == null || author.replaceAll("\\s", "").toString().length() == 0 ){
				throw new IllegalArgumentException();
			}
			this.singleAuthor = author;
			return this;
		}
		public Builder setTitle (String title) {
			if (title == null || title.replaceAll("\\s", "").toString().length() == 0) {
				throw new IllegalArgumentException();
			}
			this.title = title;
			return this;
		}
		public Builder setISBN13 ( long isbn13 ) {
			if (((String.valueOf(isbn13).length()) != 13) || (isbn13 < 0)) {
				throw new IllegalArgumentException();
			}
			this.isbn = isbn13;
			return this;
		}
		public Builder setBookType (String bookType) {
			if (bookType == null || bookType.replaceAll("\\s", "").toString().length() == 0) {
				throw new IllegalArgumentException();
			}
			if (!bookType.equals("hardcover") && !bookType.equals("paperback") && !bookType.equals("ebook")) {
				throw new IllegalArgumentException();
			}
			this.bookType = bookType;
			return this; 
		}
		
		public BookFilter build () {
			return new BookFilter(this);
		}
		
	}

}