package ec.com.technoloqie.document.loader.api.repository.dao;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;

public interface IFileStorageDao {
	
	String storeFile(MultipartFile file) throws DocumentLoaderException;

}
