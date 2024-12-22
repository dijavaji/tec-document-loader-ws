package ec.com.technoloqie.document.loader.api.service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.FileDto;

public interface IFileService {
	
	FileDto createFile(String fileName, String fileType, String filePath, Integer assistantId, String createdBy) throws DocumentLoaderException;
}
