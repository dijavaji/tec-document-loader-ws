package ec.com.technoloqie.document.loader.api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.Assistant;

public interface IAssistantRepository extends JpaRepository<Assistant, Integer>{
	
	Assistant findOneByName(String name) throws DocumentLoaderException;
}
