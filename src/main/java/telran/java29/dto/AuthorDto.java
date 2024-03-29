package telran.java29.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDto {
	String name;
	@JsonFormat(pattern = "dd-MM-yyyy")
	String birthDate;
}
