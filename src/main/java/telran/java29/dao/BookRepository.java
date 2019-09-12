package telran.java29.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import telran.java29.dto.BookResponse;
import telran.java29.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {


	List<Book> findByAuthorsName(String authorName);

	List<Book> findByPublisherPublisherName(String publisherName);

}
