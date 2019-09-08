package telran.java29.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;

@Getter
public class AuthorDto {
	String name;
	@JsonFormat(pattern = "dd-MM-yyyy")
	String birthDate;
}
