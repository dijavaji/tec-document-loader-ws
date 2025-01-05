package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.KnowledgeData;

public interface IKnowledgeDataService {
	
	List<KnowledgeData> createVectorKnowledgeData(String fileName) throws DocumentLoaderException;
}
