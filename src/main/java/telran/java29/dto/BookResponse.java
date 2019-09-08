package telran.java29.dto;

import java.util.Set;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder

public class BookResponse {
	Long isbn;
	String title;
	Set<AuthorResponse> authors;
	String publisher;
}
