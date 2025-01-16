package com.fdmgroup.main;

import java.util.ArrayList;
import java.util.List;

public class Catalog {

	private List<Book> bookList;

	private ReadItemCommand readItem;
	private WriteItemCommand writeItem;

	public Catalog(ReadItemCommand readItem, WriteItemCommand writeItem) {
		super();
		this.readItem = readItem;
		this.writeItem = writeItem;
		this.bookList = new ArrayList<>();
	}

	public List<Book> getAllBooks() {
		return readItem.readAll();

	}
	
	public void addBook(Book book) {
		writeItem.insertItem(book);
	}
	
	public void addBooks(List<Book> books) {
		for (Book b: books) {
			writeItem.insertItem(b);
		}
	}

	public Book getBook(String iSBN) {
		for (Book b: bookList) {
			if (b.getISBN().equals(iSBN)) {
				return readItem.getItem(iSBN);
			}
		}
		return null;
	}
	
	public void deleteBook(Book book) {
		writeItem.deleteItem(book);
	}
	
	public void deleteAllBooks() {
		for (Book b: bookList) {
			writeItem.deleteItem(b);
		}
	}
}
