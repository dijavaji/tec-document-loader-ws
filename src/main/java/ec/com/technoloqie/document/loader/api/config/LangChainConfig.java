package ec.com.technoloqie.document.loader.api.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.ollama.OllamaEmbeddingModel;

/**
 * @author dvasquez
 */
@Configuration
public class LangChainConfig {

	@Bean
	EmbeddingModel embeddingModel() {
		return OllamaEmbeddingModel.builder()
	            .baseUrl("http://35.211.131.67:11434")
	            .modelName("llama3:latest")
	            .build();
	}
	
	
	/*
	 * @Bean OllamaApi ollamaAiApi() { return new
	 * OllamaApi("http://35.211.131.67:11434"); } //@Bean EmbeddingModel
	 * embeddingModel(OllamaApi ollamaAiApi) { return new
	 * OllamaEmbeddingModel(ollamaAiApi, OllamaOptions.builder()
	 * .model(OllamaModel.LLAMA3_1) .build(), null, null); }
	 */

}
