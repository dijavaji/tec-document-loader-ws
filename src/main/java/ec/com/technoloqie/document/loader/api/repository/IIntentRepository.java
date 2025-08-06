package ec.com.technoloqie.document.loader.api.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.Intent;

public interface IIntentRepository extends JpaRepository<Intent, Integer>{
	
	List<Intent> findIntentByAssistantId(Integer assistantId) throws DocumentLoaderException;
}
