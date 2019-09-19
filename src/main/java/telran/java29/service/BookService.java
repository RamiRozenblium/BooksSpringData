package telran.java29.service;

import telran.java29.dto.AuthorResponse;
import telran.java29.dto.BookDto;
import telran.java29.dto.BookResponse;
import telran.java29.dto.PublisherResponse;

public interface BookService {

	public boolean addBook(BookDto bookDto);

	public BookResponse getBookByIsbn(long isbn);

	public BookResponse removeBook(long isbn);

	public Iterable<BookResponse> getBooksByPublisher(String publisherName);

	public Iterable<BookResponse> getBooksByAuthor(String author);

	public Iterable<AuthorResponse> getBookAuthors(long isbn);

	public Iterable<String> getPublishersByAuthor(String publisherName);

	public Iterable<BookResponse> getAll();
	
	public AuthorResponse deleteAuthor(String author);
	
	public PublisherResponse deletePublisher(String publisherName);

}
