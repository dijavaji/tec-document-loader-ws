package ec.com.technoloqie.document.loader.api.repository.dao.impl;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.repository.dao.IFileStorageDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileStorageDaoImpl implements IFileStorageDao{
	
	private static final String uploadDir = "uploads/";

	@Override
	public String storeFile(MultipartFile file) throws DocumentLoaderException {
		
		try {
			// Validar tipo de archivo
	        String fileType = file.getContentType();
	        if (!fileType.equals("application/pdf") && 
	            !fileType.equals("text/plain") && 
	            !fileType.equals("application/msword")) {
	            throw new IllegalArgumentException("Tipo de archivo no soportado");
	        }

	        // Crear directorio si no existe
	        File dir = new File(uploadDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

	        // Guardar archivo
	        Path path = Paths.get(uploadDir + file.getOriginalFilename());
	        Files.copy(file.getInputStream(), path);
	        return path.toString();
		}catch(IOException e) {
			log.error("Error al momento de guardar archivo en disco {}",e);
			throw new DocumentLoaderException("Error al momento de guardar archivo en disco",e);
		}
	}

}
