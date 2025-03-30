package ec.com.technoloqie.document.loader.api.dto;

import java.util.Date;

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
public class AssistantDto {
	
	private int id;
	private String name;
	private String label;
	private String description;
	private Integer languageId;
	private Integer assistantTypeId;
	private String createdBy;
	private Date createdDate;
	private String modifiedBy;
	private Date modifiedDate;
	private Boolean status;

}
