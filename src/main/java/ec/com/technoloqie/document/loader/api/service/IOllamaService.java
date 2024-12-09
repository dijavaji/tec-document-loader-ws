package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;

public interface IOllamaService {

	List<Float> createEmbedding(String text)throws DocumentLoaderException;
}
