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
public class IntentKnowlegeDto {
	
	private Integer id;
	private String name;
	private String query;
	private String response;
	private String createdBy;
	private Date createdDate;

}