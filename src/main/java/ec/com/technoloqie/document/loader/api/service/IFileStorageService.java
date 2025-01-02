package ec.com.technoloqie.document.loader.api.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.model.FileEntity;
import ec.com.technoloqie.document.loader.api.model.KnowledgeData;

public interface IFileStorageService {

	//public void init();

	void saveCsv(MultipartFile file);
	
	Collection<String> storeFiles(Collection<MultipartFile> files)throws DocumentLoaderException;
	
	List<KnowledgeData> saveDocuments(Collection<MultipartFile> files, int fileId) throws DocumentLoaderException;
	
	FileDto saveFile(MultipartFile file, Integer assistantId, String createdBy) throws DocumentLoaderException;
	
	List<FileEntity> listFiles();
	//public Resource load(String filename);
	//public void deleteAll();
	//public Stream<Path> loadAll();
}
