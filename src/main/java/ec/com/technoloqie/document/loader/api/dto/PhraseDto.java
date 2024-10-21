package ec.com.technoloqie.document.loader.api.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PhraseDto {
	
	private int id;
	private String phrase;
	private String createdBy;
	private Collection <ResponseDto> responses;
}
