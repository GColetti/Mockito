package com.fdmgroup.main;

import java.util.List;

public interface ReadItemCommand {
	List<Book> readAll();

	Book getItem(String isbn);
}
