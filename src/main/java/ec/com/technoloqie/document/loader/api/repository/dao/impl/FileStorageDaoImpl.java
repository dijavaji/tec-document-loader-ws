package ec.com.technoloqie.document.loader.api.repository.dao.impl;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.repository.dao.IFileStorageDao;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class FileStorageDaoImpl implements IFileStorageDao{
	
	@Override
	public String storeFile(MultipartFile file) throws DocumentLoaderException {
		String uploadDir = "";
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

	@Override
	public String storeFile(InputStream fileStream, String fileName, String fileType, String filePath)
			throws DocumentLoaderException {
		try {
			//if (!fileName.endsWith(".pdf") && !fileName.endsWith(".txt") && !fileName.endsWith(".doc"))
	        // Crear directorio si no existe
			String replacedDir = StringUtils.replace(filePath, fileName, ""); 
			log.info("crea directorio------{}", replacedDir);
	        File dir = new File(replacedDir);
	        if (!dir.exists()) {
	            dir.mkdirs();
	        }

			// Guardar archivo fisicamente
			Path path = Paths.get(filePath);
	        //Path path = Paths.get(uploadDir + file.getOriginalFilename());
	        //Files.copy(fileStream, path);
	        Files.write(path, fileStream.readAllBytes());
	        return path.toString();
		}catch(IOException e) {
			log.error("Error al momento de guardar archivo en disco {}",e);
			throw new DocumentLoaderException("Error al momento de guardar archivo en disco",e);
		}
	}

	@Override
	public String getEncodeFile(String path) throws DocumentLoaderException {
		String encoded = null;
		try {
			File file = new File(path);
			encoded = Base64.getEncoder().encodeToString(Files.readAllBytes(file.toPath()));
		}catch(Exception e) {
			log.error("Error de codificacion de archivo a base64 {}",e);
			throw new DocumentLoaderException("Error de codificacion de archivo a base64",e);
		}
		return encoded;
	}

}
