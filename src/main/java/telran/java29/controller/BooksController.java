package telran.java29.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java29.dto.AuthorResponse;
import telran.java29.dto.BookDto;
import telran.java29.dto.BookResponse;
import telran.java29.dto.PublisherResponse;
import telran.java29.service.BookService;

@RestController
@RequestMapping("/book")
public class BooksController {

	@Autowired
	BookService bookService;

	@PostMapping("/")
	public boolean addBook(@RequestBody BookDto bookDto) {
		return bookService.addBook(bookDto);
	}

	@GetMapping("/{isbn}")
	public BookResponse getBookByIsbn(@PathVariable long isbn) {
		return bookService.getBookByIsbn(isbn);

	}

	@PostMapping("/authors/{author}")
	public Iterable<BookResponse> getBooksByAuthor(@PathVariable String author) {
		return bookService.getBooksByAuthor(author);
	}

	@DeleteMapping("/{isbn}")
	public BookResponse removeBook(@PathVariable long isbn) {
		return bookService.removeBook(isbn);
	}

	@GetMapping("/publisher/{author}")
	public Iterable<String> getPublisherByAuthor(@PathVariable String author) {
		return bookService.getPublishersByAuthor(author);
	}

	@GetMapping("/books/{publisher}")
	public Iterable<BookResponse> getBooksByPublisher(@PathVariable String publisher) {
		return bookService.getBooksByPublisher(publisher);
	}

	@GetMapping("/books")
	public Iterable<BookResponse> getAll() {
		return bookService.getAll();
	}

	@GetMapping("/authors/{isbn}")
	public Iterable<AuthorResponse> getBookAuthors(@PathVariable long isbn) {
		return bookService.getBookAuthors(isbn);

	}

	@DeleteMapping("/authors/{author}")
	public AuthorResponse deleteBookAuthor(@PathVariable String author) {
		return bookService.deleteAuthor(author);

	}

	@DeleteMapping("/publisher/{publisher}")
	public PublisherResponse delerePublisher(@PathVariable String publisher) {
		return bookService.deletePublisher(publisher);
	}
}
