package ec.com.technoloqie.document.loader.api.service.impl;


import java.util.List;

import org.springframework.stereotype.Service;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.output.Response;
import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.service.IOllamaService;

@Service
public class OllamaServiceImpl implements IOllamaService{
	
	private EmbeddingModel embeddingModel;
	
	public OllamaServiceImpl( EmbeddingModel embeddingModel) {
		this.embeddingModel = embeddingModel;
	}
	
	@Override
	public List<Float> createEmbedding(String text) throws DocumentLoaderException {
		Response<Embedding> response = embeddingModel.embed(text);
		return response.content().vectorAsList();
	}

}
