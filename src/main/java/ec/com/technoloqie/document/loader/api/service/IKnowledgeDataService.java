package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import org.springframework.ai.document.Document;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.KnowledgeData;

public interface IKnowledgeDataService {
	
	List<KnowledgeData> createVectorKnowledgeData(String fileName) throws DocumentLoaderException;
	
	List<KnowledgeData> createVectorKnowledgeData(List<Document> documents) throws DocumentLoaderException;
}
