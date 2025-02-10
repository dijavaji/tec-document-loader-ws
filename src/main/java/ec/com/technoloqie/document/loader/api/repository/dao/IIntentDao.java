package ec.com.technoloqie.document.loader.api.repository.dao;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.Intent;

public interface IIntentDao {
	
	Intent getIntentById(Integer id) throws DocumentLoaderException;
	
	List<Intent> findIntentsKnowlegeByAssistant(Integer assistantId) throws DocumentLoaderException;

}
