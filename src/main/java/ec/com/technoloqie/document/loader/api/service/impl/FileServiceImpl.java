package ec.com.technoloqie.document.loader.api.service.impl;



import java.util.List;

import org.springframework.stereotype.Service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.mapper.FileMapper;
import ec.com.technoloqie.document.loader.api.model.Assistant;
import ec.com.technoloqie.document.loader.api.model.FileEntity;
import ec.com.technoloqie.document.loader.api.repository.IFileRepository;
import ec.com.technoloqie.document.loader.api.service.IFileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements IFileService{
	
	private IFileRepository fileRepository;
	
	public FileServiceImpl(IFileRepository fileRepository) {
		this.fileRepository = fileRepository;
	}

	@Override
	public FileDto createFile(String fileName, String fileType, String filePath, Integer assistantId, String createdBy)
			throws DocumentLoaderException {
		FileDto newIntent = null;
		//TODO buscar asistente
		try {
			FileEntity fileEntity = FileMapper.mapToFile(fileName, fileType, filePath, createdBy, new Assistant());
			FileEntity savedIntent = this.fileRepository.save(fileEntity);
			newIntent = FileMapper.mapToFileDto(savedIntent);
		}catch(Exception e) {
			log.error("Error al momento de guardar archivo",e);
			throw new DocumentLoaderException("Error al momento de guardar archivo",e);
		}
		return newIntent;
	}

	@Override
	public List<FileDto> getFileByAssistantName(String assistantName) throws DocumentLoaderException {
		List<FileEntity> files = this.fileRepository.findFileByAssistantName(assistantName);
		return files.stream().map( FileMapper::mapToFileDto).toList();
	}

}
