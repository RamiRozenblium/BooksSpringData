package telran.java29.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import telran.java29.dto.BookDto;
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
}
