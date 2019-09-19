package telran.java29.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.objenesis.instantiator.annotations.Typology;
import org.springframework.stereotype.Repository;

import telran.java29.model.Book;

@Repository
public class BookRepositoryImpl implements BookRepository {

	@PersistenceContext
	EntityManager em;

	@Override
	public List<Book> findByAuthorsName(String authorName) {
		TypedQuery<Book> q = em.createQuery("select b from Book b join b.authors a where a.name=?1", Book.class);
		q.setParameter(1, authorName);
		return q.getResultList();
	}

	@Override
	public List<Book> findByPublisherPublisherName(String publisherName) {
		TypedQuery<Book> q = em.createQuery("select b from Book b join b.publisher p where p.publisherName=?1", Book.class);
		q.setParameter(1, publisherName);
		return q.getResultList();
	}

	@Override
	public Optional<Book> findById(Long isbn) {
		Book book = em.find(Book.class, isbn);
		return Optional.ofNullable(book);
	}

	@Override
	public Book save(Book book) {
		em.persist(book);
		return book;
	}

	@Override
	public void deleteById(long isbn) {
		Book book = em.find(Book.class, isbn);
		em.remove(book);

	}

	@Override
	public List<Book> findAll() {
		TypedQuery<Book> q = em.createQuery("select b from Book b", Book.class);
		return q.getResultList();
	}

	@Override
	public boolean existById(long isbn) {
		return em.find(Book.class, isbn) != null;
	}

}
