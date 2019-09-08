package telran.java29.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java29.dao.AuthorRepository;
import telran.java29.dao.BookRepository;
import telran.java29.dao.PublisherRepository;
import telran.java29.dto.AuthorDto;
import telran.java29.dto.BookDto;
import telran.java29.model.Author;
import telran.java29.model.Book;
import telran.java29.model.Publisher;

@Service
public class BookServiceImp implements BookService {

	@Autowired
	BookRepository bookRepository;

	@Autowired
	AuthorRepository authorRepository;

	@Autowired
	PublisherRepository publisherRepository;

	@Override
	@Transactional
	public boolean addBook(BookDto bookDto) {

		Book book = bookRepository.findById(bookDto.getIsbn()).orElse(null);
		if (book == null) {
			checkAuthors(bookDto.getAuthors());
			Publisher p = checkPublisher(bookDto);
			createBook(bookDto, p);
			return true;
		}
		return false;
	}

	private void createBook(BookDto bookDto, Publisher p) {
		Book book = Book.builder().isbn(bookDto.getIsbn()).title(bookDto.getTitle()).publisher(p)
				.authors(convertAuthorsDtoToAuthors(bookDto.getAuthors())).build();
		
		bookRepository.save(book);

	}

	private Set<Author> convertAuthorsDtoToAuthors(Set<AuthorDto> authors) {

		return authors.stream().map(this::convetToAuthor).collect(Collectors.toSet());
	}

	private Publisher checkPublisher(BookDto b) {
		Publisher publisher = publisherRepository.findById(b.getPublisher()).orElse(null);
		if (publisher == null) {
			Publisher p = createPublisher(b);
			publisher = publisherRepository.save(p);
		}
		return publisher;

	}

	private Publisher createPublisher(BookDto b) {
		Publisher p = Publisher.builder().publisherName(b.getPublisher()).build();
		return p;
	}

	private void checkAuthors(Set<AuthorDto> authors) {

		authors.stream().filter(a -> authorRepository.findById(a.getName()) == null).map(this::convetToAuthor)
				.forEach(a -> authorRepository.save(a));
	}

	private Author convetToAuthor(AuthorDto a) {
		return Author.builder().name(a.getName()).dateBirth(LocalDate.parse(a.getBirthDate(), DateTimeFormatter.ofPattern("ddMMyyyy"))).build();

	}
}
