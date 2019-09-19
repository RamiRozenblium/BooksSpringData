package telran.java29.dao;

import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import telran.java29.model.Publisher;

@Repository
public class PublisherRepositoryImpl implements PublisherRepository {
	@PersistenceContext
	EntityManager em;

	@Override
	public List<String> findPublishersByAuthor(String name) {
		TypedQuery<String> q = em.createQuery(
				"select distinct p.publisherName from Book b join b.publisher p join b.authors a where a.name =?1",
				String.class);
		q.setParameter(1, name);
		return q.getResultList();
	}

	@Override
	public Optional<Publisher> findById(String publisherName) {
		Publisher publisher = em.find(Publisher.class, publisherName);
		return Optional.ofNullable(publisher);
	}

	@Override
	public Publisher save(Publisher p) {
		em.persist(p);
		return p;
	}

	@Override
	public void deleteById(String publisherName) {
		Publisher p = em.find(Publisher.class, publisherName);
		em.remove(p);

	}

}
