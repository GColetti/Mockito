package com.fdmgroup.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.invocation.Invocation;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fdmgroup.main.Book;
import com.fdmgroup.main.Catalog;
import com.fdmgroup.main.ReadItemCommand;
import com.fdmgroup.main.WriteItemCommand;

@ExtendWith(MockitoExtension.class)
class CatalogueTest {
	ReadItemCommand readItemMock = null;
	WriteItemCommand writeItemMock = null;
	Catalog catalogObj = null;

	@BeforeEach
	void setUp() throws Exception {
		readItemMock = Mockito.mock(ReadItemCommand.class);
		writeItemMock = Mockito.mock(WriteItemCommand.class);
		catalogObj = new Catalog(readItemMock, writeItemMock);
	}

	@Test
	@DisplayName("Test 1")
	void getAllBooks_ReturnsEmptyBookList_IfNoBooksAreInTheCatalogue() {

		/// Arrange
		// You will need a Catalogue object Act
		// Call getAllBooks() method of your Catalogue object and store the returned
		/// Book list
		// Assert
		// Assert that the size of the Book list should be equal to zero

		// Act
		catalogObj.getAllBooks();

		// Assert
		assertEquals(0, catalogObj.getAllBooks().size());

	}

	@Test
	@DisplayName("Test 2")
	public void getAllBooks_CallsReadAllMethodOfReadItemCommand_WhenCalled() {
		// Act
		// Call getAllBooks() method of your catalogue. You can ignore its return value.
		// Assert
		// Verify that the readAll() method of ReadItemCommand is called exactly once,
		// with no arguments.

		// Act
		catalogObj.getAllBooks();

		// Assert
		Mockito.verify(readItemMock, Mockito.times(1)).readAll();
	}

	@Test
	@DisplayName("Test 3")
	public void getAllBooks_ReturnsListOfBooksItReceivesFromReadAllMethodOfReadItemCommand_WhenCalled() {
		// Arrange
		List<Book> mockBookList = Mockito.mock(List.class);

		// Stub
		when(readItemMock.readAll()).thenReturn(mockBookList);

		// Act
		List<Book> resultList = catalogObj.getAllBooks();

		// Assert
		assertEquals(mockBookList, resultList);
	}

	@Test
	@DisplayName("Test 4")
	public void addBook() {
		// Arrange
		Book mockBook = Mockito.mock(Book.class);

		// Act
		catalogObj.addBook(mockBook);

		// Assert
		Mockito.verify(writeItemMock, Mockito.times(1)).insertItem(mockBook);
	}

	@Test
	@DisplayName("Test 5")
	public void addBooks_IsCalledNumberOfTimes_OfBooksInBookList() {
		// Arrange
		List<Book> mockBookList = new ArrayList<Book>();
		Book mockBook = Mockito.mock(Book.class);
		Book mockBook2 = Mockito.mock(Book.class);
		mockBookList.add(mockBook);
		mockBookList.add(mockBook2);

		// Act
		catalogObj.addBooks(mockBookList);

		Collection<Invocation> invocations = Mockito.mockingDetails(writeItemMock).getInvocations();
		int numberOfCalls = invocations.size();

		// Assert
		assertEquals(numberOfCalls, mockBookList.size());

	}

	@Test
	@DisplayName("Test 6")
	public void getBook_ReturnsABookWithSameIBSNFromGetItemInReadItemCommand_WhenCalled() {
		// Arrange
		Book mockBook = Mockito.mock(Book.class);
		mockBook.setISBN("9781234567897");
		catalogObj.addBook(mockBook);

		// Stub
		when(readItemMock.getItem("9781234567897")).thenReturn(mockBook);

		// Act
		Book resultBook = catalogObj.getBook("9781234567897");

		// Assert
		Mockito.verify(readItemMock, Mockito.times(1)).getItem("9781234567897");
		assertEquals(mockBook.getISBN(), resultBook.getISBN());
	}

	@Test
	@DisplayName("Test 7")
	public void deleteBook_TakesBookAsArgument_CallsWriteItemCommandDeleteItem() {
		// Arrange
		Book mockBook = Mockito.mock(Book.class);

		// Act
		catalogObj.deleteBook(mockBook);

		// Assert
		Mockito.verify(writeItemMock, Mockito.times(1)).deleteItem(mockBook);
	}

	@Test
	@DisplayName("Test 8")
	public void deleteAllBooks_TakesBookListAsArgument_CallsWriteItemCommandDeleteItem() {
		// Arrange
		List<Book> mockBookList = Mockito.mock(List.class);
		Book mockBook = Mockito.mock(Book.class);

		// Stubb
		when(readItemMock.readAll()).thenReturn(mockBookList);

		List<Book> returnList = catalogObj.getAllBooks();

		// Act
		catalogObj.deleteAllBooks();

		Collection<Invocation> invocations = Mockito.mockingDetails(writeItemMock).getInvocations();
		int numberOfCalls = invocations.size();

		// Assert
		assertEquals(numberOfCalls, mockBookList.size());
	}

}
