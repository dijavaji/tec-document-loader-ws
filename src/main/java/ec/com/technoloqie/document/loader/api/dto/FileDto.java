package ec.com.technoloqie.document.loader.api.dto;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class FileDto {
	
	private MultipartFile file;
	private int assistantId;
	private String createdBy;
}
