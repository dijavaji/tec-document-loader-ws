package ec.com.technoloqie.document.loader.api.mapper;

import java.util.Date;

import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.model.Assistant;
import ec.com.technoloqie.document.loader.api.model.FileEntity;

public class FileMapper {
	
	private FileMapper() {
		
	}
	
	public static FileEntity mapToFile(String fileName, String fileType, String filePath, String createdBy, Assistant assistant) {
		FileEntity fileEntity = new FileEntity();
		//fileEntity.setAssistantId(assistantId);
		fileEntity.setAssistant(assistant);
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
		fileDto.setAssistant(AssistantMapper.mapToAssistantDto(savedFile.getAssistant()));
		fileDto.setCreatedBy(savedFile.getCreatedBy());
		fileDto.setCreatedDate(savedFile.getCreatedDate());
		return fileDto;
	}

}
