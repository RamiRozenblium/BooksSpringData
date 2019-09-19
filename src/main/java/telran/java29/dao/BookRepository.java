package telran.java29.dao;

import java.util.List;
import java.util.Optional;

import telran.java29.model.Book;

public interface BookRepository  {


	List<Book> findByAuthorsName(String authorName);

	List<Book> findByPublisherPublisherName(String publisherName);

	Optional<Book> findById(Long isbn);

	Book save(Book book);

	void deleteById(long isbn);

	List<Book> findAll();
	
	boolean existById(long isbn);

}
