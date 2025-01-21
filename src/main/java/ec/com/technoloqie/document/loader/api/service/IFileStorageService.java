package ec.com.technoloqie.document.loader.api.service;

import java.util.Collection;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.model.KnowledgeData;

public interface IFileStorageService {

	//public void init();

	void save(MultipartFile file);
	
	Collection<String> storeFiles(Collection<MultipartFile> files);
	
	List<KnowledgeData> storeDocuments(Collection<MultipartFile> files);
	//public Resource load(String filename);
	//public void deleteAll();
	//public Stream<Path> loadAll();
}
