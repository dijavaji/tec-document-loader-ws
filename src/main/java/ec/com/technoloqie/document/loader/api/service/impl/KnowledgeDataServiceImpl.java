package ec.com.technoloqie.document.loader.api.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.ai.document.Document;
import org.springframework.ai.reader.TextReader;
import org.springframework.ai.transformer.splitter.TextSplitter;
import org.springframework.ai.transformer.splitter.TokenTextSplitter;
import org.springframework.stereotype.Service;

import ec.com.technoloqie.document.loader.api.commons.exception.DocumentLoaderException;
import ec.com.technoloqie.document.loader.api.model.KnowledgeData;
import ec.com.technoloqie.document.loader.api.repository.IKnowledgeDataRepository;
import ec.com.technoloqie.document.loader.api.service.IKnowledgeDataService;
import ec.com.technoloqie.document.loader.api.service.IOllamaService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class KnowledgeDataServiceImpl implements IKnowledgeDataService{
	
	private IKnowledgeDataRepository knowledgeDataRepository;
	//private IAIService iAIService;
	private IOllamaService ollamaService;
	
	public KnowledgeDataServiceImpl(IKnowledgeDataRepository knowledgeDataRepository, 
			IOllamaService ollamaService){
		this.ollamaService = ollamaService;
		this.knowledgeDataRepository = knowledgeDataRepository;
	}

	@Override
	public List<KnowledgeData> createVectorKnowledgeData(String fileName) throws DocumentLoaderException {
				TextReader textReader = new TextReader(fileName);
		        textReader.getCustomMetadata().put("filename", fileName);
		        List<Document> documents = textReader.get();
		        //TextSplitter textSplitter = new TokenTextSplitter(); //por defecto toma TokenTextSplitter(800, 350, 5, 10000, true);
		        TextSplitter textSplitter =  new TokenTextSplitter(1000, 1000, 5, 1000, true);//new TokenTextSplitter(300, 300, 5, 1000, true);
		        List<Document> splitDocuments = textSplitter.apply(documents);
		        //splitDocuments.stream().forEach(doc -> {doc.setEmbedding(embedder.createEmbedding(doc.getContent()).block());});
				List<KnowledgeData> embedDocuments = splitDocuments.stream().map(doc -> {
					KnowledgeData nuevoDoc = KnowledgeData.fromDomainModel(doc, 0);
					//nuevoDoc.setEmbedding(this.iAIService.createEmbedding(doc.getText()).block());
					nuevoDoc.setEmbedding(this.ollamaService.createEmbedding(doc.getText()));
					return nuevoDoc;
				}).collect(Collectors.toList());
		        embedDocuments.stream().forEach(doc -> knowledgeDataRepository.save(doc));
				return embedDocuments;
	}

	@Override
	public List<KnowledgeData> createVectorKnowledgeData(List<Document> documents, int fileId) throws DocumentLoaderException {
		TextSplitter textSplitter =  new TokenTextSplitter(1000, 1000, 5, 1000, true);//new TokenTextSplitter(300, 300, 5, 1000, true);
        List<Document> splitDocuments = textSplitter.apply(documents);
        //splitDocuments.stream().forEach(doc -> {doc.setEmbedding(embedder.createEmbedding(doc.getContent()).block());});
		List<KnowledgeData> embedDocuments = splitDocuments.stream().map(doc -> {
			KnowledgeData nuevoDoc = KnowledgeData.fromDomainModel(doc, fileId);
			//nuevoDoc.setEmbedding(this.iAIService.createEmbedding(doc.getText()).block());
			nuevoDoc.setEmbedding(this.ollamaService.createEmbedding(doc.getText()));
			return nuevoDoc;
		}).collect(Collectors.toList());
		log.info("DocumentSplitter {}", embedDocuments.size());
		return knowledgeDataRepository.saveAll(embedDocuments);
	}

}
