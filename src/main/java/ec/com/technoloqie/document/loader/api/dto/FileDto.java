package ec.com.technoloqie.document.loader.api.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDto {
	
	private int id;
	private int assistantId;
	private String fileName;
	private String fileType;
	private String filePath;
	private String createdBy;
	private Date createdDate;
}
