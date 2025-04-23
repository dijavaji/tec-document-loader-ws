package ec.com.technoloqie.document.loader.api.service.impl;



import java.util.List;

import org.springframework.stereotype.Service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.AssistantDto;
import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.mapper.AssistantMapper;
import ec.com.technoloqie.document.loader.api.mapper.FileMapper;
import ec.com.technoloqie.document.loader.api.model.FileEntity;
import ec.com.technoloqie.document.loader.api.repository.IFileRepository;
import ec.com.technoloqie.document.loader.api.service.IAssistantService;
import ec.com.technoloqie.document.loader.api.service.IFileService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FileServiceImpl implements IFileService{
	
	private IFileRepository fileRepository;
	private IAssistantService assistantService;
	
	public FileServiceImpl(IFileRepository fileRepository, IAssistantService assistantService) {
		this.fileRepository = fileRepository;
		this.assistantService = assistantService;
	}

	@Override
	public FileDto createFile(String fileName, String fileType, String filePath, Integer assistantId, String createdBy)
			throws DocumentLoaderException {
		FileDto newIntent = null;
		AssistantDto assistant = this.assistantService.getAssistantById(assistantId);
		try {
			FileEntity fileEntity = FileMapper.mapToFile(fileName, fileType, filePath, createdBy,AssistantMapper.mapToAssistant(assistant));
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

	@Override
	public FileDto getFileById(Integer id) throws DocumentLoaderException {
		FileEntity fileEntity = this.fileRepository.findById(id).orElseThrow(()-> new DocumentLoaderException("Error el archivo no existe"));
		return FileMapper.mapToFileDto(fileEntity);
	}

}
