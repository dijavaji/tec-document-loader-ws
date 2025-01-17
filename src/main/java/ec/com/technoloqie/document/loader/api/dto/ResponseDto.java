package ec.com.technoloqie.document.loader.api.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseDto {
	
	private int id;
	private String response;
	private String createdBy;
	private PhraseDto phrase;
}
