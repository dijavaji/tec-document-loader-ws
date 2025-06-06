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
public class FileDto {
	
	private int id;
	private AssistantDto assistant;
	private String fileName;
	private String fileType;
	private String filePath;
	private String createdBy;
	private Date createdDate;
	private String fileBase64;
}
