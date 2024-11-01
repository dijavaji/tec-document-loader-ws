package ec.com.technoloqie.document.loader.api.dto;

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
	private String createdBy;
	
}
