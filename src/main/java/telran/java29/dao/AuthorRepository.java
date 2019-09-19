package telran.java29.dao;

import java.util.Optional;

import telran.java29.model.Author;

public interface AuthorRepository  {

	Optional<Author> findById(String name);

	Author save(Author newAuthor);

	void delete(Author author);

}
