/**
 * 
 */
package com.fdmgroup.main;


public interface WriteItemCommand {
	
	void insertItem(Book book);
	
	void deleteItem(Book book);
}
