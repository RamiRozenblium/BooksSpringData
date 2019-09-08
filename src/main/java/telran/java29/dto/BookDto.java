package telran.java29.dto;

import java.util.Set;

import lombok.Getter;

@Getter
public class BookDto {
	Long isbn;
	String title;
	Set<AuthorDto> authors;
	String publisher;
}
