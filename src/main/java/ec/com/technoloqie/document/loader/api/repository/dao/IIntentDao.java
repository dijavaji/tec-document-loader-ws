package ec.com.technoloqie.document.loader.api.repository.dao;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.Intent;

public interface IIntentDao {
	
	Intent getIntentById(Integer id) throws DocumentLoaderException;

}
