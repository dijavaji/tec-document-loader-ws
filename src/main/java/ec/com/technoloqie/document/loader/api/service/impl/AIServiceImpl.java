package ec.com.technoloqie.document.loader.api.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.service.IAIService;
import reactor.core.publisher.Mono;

@Service
public class AIServiceImpl implements IAIService{

	@Override
	public Mono<List<Double>> createEmbedding(String text) throws DocumentLoaderException {
		// TODO Auto-generated method stub
		return null;
	}

}
