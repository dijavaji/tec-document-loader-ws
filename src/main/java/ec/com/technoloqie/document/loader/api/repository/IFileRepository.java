package ec.com.technoloqie.document.loader.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.FileEntity;

public interface IFileRepository extends JpaRepository<FileEntity, Integer>{
	
	List<FileEntity> findFileByAssistantName(String assistantName) throws DocumentLoaderException;
}
