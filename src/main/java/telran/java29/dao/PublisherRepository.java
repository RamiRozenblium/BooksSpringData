package telran.java29.dao;

import java.util.List;
import java.util.Optional;

import telran.java29.model.Publisher;

public interface PublisherRepository {

	
	List<String> findPublishersByAuthor(String name);

	Optional<Publisher> findById(String publisher);

	Publisher save(Publisher p);

	void deleteById(String publisherName);
}
