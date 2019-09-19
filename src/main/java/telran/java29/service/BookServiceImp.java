package telran.java29.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
import telran.java29.dto.PublisherResponse;
import telran.java29.exception.DataException;
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

		return convertBookToBookResponse(book);
	}

	private BookResponse convertBookToBookResponse(Book book) {
		Set<AuthorResponse> author = convertAuthorsToAuthorsResponse(book.getAuthors());
		return BookResponse.builder().isbn(book.getIsbn()).title(book.getTitle()).authors(author)
				.publisher(book.getPublisher().getPublisherName()).build();
	}

	private Set<AuthorResponse> convertAuthorsToAuthorsResponse(Set<Author> authors) {

		return authors.stream().map(this::createAuthorResponse).collect(Collectors.toSet());
	}

	private AuthorResponse createAuthorResponse(Author a) {

		return AuthorResponse.builder().author(a.getName()).build();
	}

	@Override
	public Iterable<BookResponse> getBooksByAuthor(String authorName) {
//		Author author = authorRepository.findById(authorName).orElse(null);
//		if (author != null) {
//			author.getBooks().stream().map(this::convertBookToBookResponse).collect(Collectors.toList);
//		}
//		return new ArrayList<>();

		return bookRepository.findByAuthorsName(authorName).stream().map(this::convertBookToBookResponse)
				.collect(Collectors.toList());

	}

	@Override
	@Transactional
	public BookResponse removeBook(long isbn) {
		Book book = bookRepository.findById(isbn).orElseThrow(() -> new DataException());
		bookRepository.deleteById(isbn);
		return convertBookToBookResponse(book);
	}

	@Override
	public Iterable<BookResponse> getBooksByPublisher(String publisherName) {
		return bookRepository.findByPublisherPublisherName(publisherName).stream().map(this::convertBookToBookResponse)
				.collect(Collectors.toList());
	}

	@Override
	public Iterable<AuthorResponse> getBookAuthors(long isbn) {
		Book book = bookRepository.findById(isbn).orElse(null);
		if (book != null) {
			return book.getAuthors().stream().map(a -> createAuthorResponse(a)).collect(Collectors.toList());
		}
		return new ArrayList<AuthorResponse>();
	}

	@Override
	public Iterable<String> getPublishersByAuthor(String publisherName) {

		return publisherRepository.findPublishersByAuthor(publisherName);
	}

	@Override
	public Iterable<BookResponse> getAll() {

		return bookRepository.findAll().stream().map(this::convertBookToBookResponse).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public AuthorResponse deleteAuthor(String authorName) {
		Author author = authorRepository.findById(authorName).orElse(null);
		if (author != null) {
			Iterable<BookResponse> books = getBooksByAuthor(authorName);
			StreamSupport.stream(books.spliterator(), false).forEach(b -> bookRepository.deleteById(b.getIsbn()));
			return createAuthorResponse(author);
		}

		return null;
	}

	@Override
	@Transactional
	public PublisherResponse deletePublisher(String publisherName) {

		Publisher publisher = publisherRepository.findById(publisherName).orElse(null);
		if (publisher != null) {
			// find and delete all book by this publisher
			List<Book> books = bookRepository.findByPublisherPublisherName(publisherName);
			books.forEach(b -> {

				b.getAuthors().stream().forEach(a -> deleteAuthor(a.getName()));
			});
			
			publisherRepository.deleteById(publisherName);

			return convertPublisherToPublisherResponse(publisher);
		}
		return null;
	}

	private PublisherResponse convertPublisherToPublisherResponse(Publisher publisher) {

		return PublisherResponse.builder().publisherName(publisher.getPublisherName()).build();
	}
}
