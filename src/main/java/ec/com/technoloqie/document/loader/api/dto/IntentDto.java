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
public class IntentDto {
	
	private Integer id;
	private String name;
	private String description;
	private int assistantId;
	private Collection <PhraseDto> phrases;
	private String createdBy;
	private String modifiedBy;
	
}
