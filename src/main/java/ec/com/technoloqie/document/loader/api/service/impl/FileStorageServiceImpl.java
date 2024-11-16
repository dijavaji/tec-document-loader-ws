package ec.com.technoloqie.document.loader.api.service.impl;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.service.IFileStorageService;

@Service
public class FileStorageServiceImpl implements IFileStorageService{
	
	private final Path root = Paths.get("uploads");

	@Override
	public void save(MultipartFile file) {
		try {
		      Files.copy(file.getInputStream(), this.root.resolve(file.getOriginalFilename()));
		    } catch (Exception e) {
		      if (e instanceof FileAlreadyExistsException) {
		        throw new RuntimeException("A file of that name already exists.");
		      }

		      throw new RuntimeException(e.getMessage());
		    }
	}

}
