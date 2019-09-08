package telran.java29.model;

import java.io.Serializable;
import java.util.Collection;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@EqualsAndHashCode(of = { "isbn" })
@Entity
public class Book implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	long isbn;
	String title;
	@ManyToMany
	Set<Author> authors;
	@ManyToOne
	Publisher publisher;

	public boolean addAuthor(Author author){
		return authors.add(author);
	}

	public boolean addAllAuthor(Collection<Author> authors){
		return authors.addAll(authors);
	}
}
