package telran.java29.service;

import telran.java29.dto.BookDto;
import telran.java29.dto.BookResponse;

public interface BookService {
	public boolean addBook(BookDto bookDto);

	public BookResponse getBookByIsbn(long isbn);
}
