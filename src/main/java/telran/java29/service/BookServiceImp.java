package telran.java29.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import telran.java29.dao.AuthorRepository;
import telran.java29.dao.BookRepository;
import telran.java29.dao.PublisherRepository;
import telran.java29.dto.AuthorDto;
import telran.java29.dto.AuthorResponse;
import telran.java29.dto.BookDto;
import telran.java29.dto.BookResponse;
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
			createBook(bookDto);
			return true;
		}
		return false;
	}

	private void createBook(BookDto bookDto) {
		Publisher p = checkPublisher(bookDto);
		Set<Author> authors = checkAuthors(bookDto.getAuthors());
		Book book = Book.builder().isbn(bookDto.getIsbn()).title(bookDto.getTitle()).publisher(p).authors(authors)
				.build();
		System.out.println(book);
		bookRepository.save(book);

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

	private Set<Author> checkAuthors(Set<AuthorDto> authors) {
		Set<Author> auths = new HashSet<>();

		authors.stream().forEach(a -> {
			Author author = authorRepository.findById(a.getName()).orElse(null);
			if (author == null) {
				Author newAuthor = createAuthor(a);
				authorRepository.save(newAuthor);
				auths.add(newAuthor);
			}
			auths.add(author);

		});

		return auths;
	}

	private Author createAuthor(AuthorDto a) {
		return Author.builder().name(a.getName())
				.dateBirth(LocalDate.parse(a.getBirthDate(), DateTimeFormatter.ofPattern("ddMMyyyy"))).build();

	}

	@Override
	public BookResponse getBookByIsbn(long isbn) {
		Book book = bookRepository.findById(isbn).get();

		return convertBookToBookDto(book);
	}

	private BookResponse convertBookToBookDto(Book book) {
		Set<AuthorResponse> author = convertAuthorsToAuthorsDto(book.getAuthors());
		return BookResponse.builder().isbn(book.getIsbn()).title(book.getTitle()).authors(author)
				.publisher(book.getPublisher().getPublisherName()).build();
	}

	private Set<AuthorResponse> convertAuthorsToAuthorsDto(Set<Author> authors) {

		return authors.stream().map(a -> createAuthorDto(a)).collect(Collectors.toSet());
	}

	private AuthorResponse createAuthorDto(Author a) {

		return AuthorResponse.builder().author(a.getName()).build();
	}

	@Override
	public Iterable<BookResponse> getBooksByAuthor(String authorName) {
		List<BookResponse> res = new ArrayList<>();
		Author author = authorRepository.findById(authorName).get();
		if (author != null) {
			author.getBooks().forEach(b -> res.add(convertBookToBookDto(b)));
		}
		return res;
	}
}
