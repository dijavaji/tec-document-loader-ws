package ec.com.technoloqie.document.loader.api.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ResponseDto {
	
	private int id;
	private String response;
	private String createdBy;
	private PhraseDto phrase;
}
