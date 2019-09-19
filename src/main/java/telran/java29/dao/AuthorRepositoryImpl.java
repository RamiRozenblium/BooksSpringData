package telran.java29.dao;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import telran.java29.model.Author;

@Repository
public class AuthorRepositoryImpl implements AuthorRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public Optional<Author> findById(String name) {

		return Optional.ofNullable(em.find(Author.class, name));
	}

	@Override
	public Author save(Author newAuthor) {
		em.persist(newAuthor);
		return newAuthor;
	}

	@Override
	public void delete(Author author) {
		em.remove(author);

	}

}