package telran.java29.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter

@Setter
@Builder
@EqualsAndHashCode(of = { "isbn" })
@Entity
public class Book {
	@Id
	long isbn;
	String title;
	@ManyToMany(cascade = CascadeType.ALL)
	Set<Author> authors;
	@ManyToOne
	Publisher publisher;

//	public boolean addAuthor(Author author){
//		return authors.add(author);
//	}
//
//	public boolean addAllAuthor(Collection<Author> authors){
//		return authors.addAll(authors);
//	}
}
