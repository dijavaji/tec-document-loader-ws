package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.model.FileEntity;

public class FileMapper {
	
	private FileMapper() {
		
	}
	
	public static FileEntity mapToFile(String fileName, String fileType, String filePath, Integer assistantId, String createdBy) {
		FileEntity fileEntity = new FileEntity();
		fileEntity.setAssistantId(assistantId);
		fileEntity.setFileName(fileName);
		fileEntity.setFilePath(filePath);
		fileEntity.setFileType(fileType);
		fileEntity.setCreatedDate(new Date());
		fileEntity.setCreatedBy(createdBy);
		return fileEntity;
	}

	public static FileDto mapToFileDto(FileEntity savedFile) {
		FileDto fileDto = new FileDto();
		fileDto.setId(savedFile.getId());
		fileDto.setFileName(savedFile.getFileName());
		fileDto.setFilePath(savedFile.getFilePath());
		fileDto.setFileType(savedFile.getFileType());
		fileDto.setAssistantId(savedFile.getAssistantId());
		fileDto.setCreatedBy(savedFile.getCreatedBy());
		return fileDto;
	}

}
