package ec.com.technoloqie.document.loader.api.service;

import java.util.List;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import reactor.core.publisher.Mono;

public interface IAIService {
	
	Mono<List<Double>> createEmbedding(String text)throws DocumentLoaderException;
}
