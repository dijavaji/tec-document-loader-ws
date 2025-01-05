package ec.com.technoloqie.document.loader.api.service;

import java.util.Collection;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

	//public void init();

	void save(MultipartFile file);
	
	Collection<String> storeFiles(Collection<MultipartFile> files);

	//public Resource load(String filename);

	//public void deleteAll();

	//public Stream<Path> loadAll();
}
