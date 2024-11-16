package ec.com.technoloqie.document.loader.api.service;

import org.springframework.web.multipart.MultipartFile;

public interface IFileStorageService {

	//public void init();

	public void save(MultipartFile file);

	//public Resource load(String filename);

	//public void deleteAll();

	//public Stream<Path> loadAll();
}
