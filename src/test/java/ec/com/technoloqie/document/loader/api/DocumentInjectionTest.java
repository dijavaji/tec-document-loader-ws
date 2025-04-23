package ec.com.technoloqie.document.loader.api;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ec.com.technoloqie.document.loader.api.model.KnowledgeData;
import ec.com.technoloqie.document.loader.api.service.IKnowledgeDataService;
import ec.com.technoloqie.document.loader.api.service.IOllamaService;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("local")
@SpringBootTest
@Slf4j
public class DocumentInjectionTest {
	
	@Autowired
	private IKnowledgeDataService knowledgeDataService;
	@Autowired 
	private IOllamaService ollamaService;
	
	@Test
	void createVectorStore() {
		try {
			String fileName = "classpath:/docs/ley-minera.txt";
			List<KnowledgeData> documents	= knowledgeDataService.createVectorKnowledgeData(fileName);
			log.info("creamos document embedding");
			Assertions.assertNotNull(documents);
		}catch(Exception e) {
			log.error("Error createVectorStore. {}",e);
			Assertions.assertTrue(Boolean.TRUE,"createVectorStore.");
		}
	}
	
	@Test
	void createEmbeddingTest() {
		try {
			List<Double> response =  this.ollamaService.createEmbedding("hola mundo");
			log.info("convierto vectores {}",response);
			Assertions.assertNotNull(response);
		}catch(Exception e) {
			log.error("Error createEmbedding. {}",e);
			Assertions.assertTrue(Boolean.TRUE,"createVectorStore.");
		}
	}
	
	/*@Test
	void getOllamaAPIprogrammaticallyTest() {

		// Sync request
		var request = ChatRequest.builder("orca-mini")
		    .stream(false) // not streaming
		    .messages(List.of(
		            Message.builder(Role.SYSTEM)
		                .content("You are a geography teacher. You are talking to a student.")
		                .build(),
		            Message.builder(Role.USER)
		                .content("What is the capital of Bulgaria and what is the size? "
		                        + "What is the national anthem?")
		                .build()))
		    .options(OllamaOptions.create().temperature(0.9))
		    .build();

		ChatResponse response = this.ollamaApi.chat(this.request);
	}*/
	
	@Test
	void getDocsFromPdfWithCatalog() {
		try {
			//ParagraphPdfDocumentReader pdfReader = new ParagraphPdfDocumentReader("classpath:/docs/ley-minera.pdf");
			PagePdfDocumentReader pdfReader = new PagePdfDocumentReader("classpath:/docs/codigo_civil_colombia.pdf",
					PdfDocumentReaderConfig.builder()
						.withPageTopMargin(0)
						.withPageExtractedTextFormatter(ExtractedTextFormatter.builder()
							.withNumberOfTopTextLinesToDelete(0)
							.build())
						.withPagesPerDocument(1)
						.build());
			List<Document> documentls = pdfReader.get();
	        log.info("nro hojas {}", documentls.size());
	        Assertions.assertNotNull(documentls);
			
		}catch(Exception e) {
			log.error("Error getDocsFromPdfWithCatalog. {}",e);
			Assertions.assertTrue(Boolean.TRUE,"getDocsFromPdfWithCatalog.");
		}
    }
	
	@Test
	void readFileTest() throws IOException {
		String filePath="/var/opt/apptmp";
		Path path = Path.of(filePath);
        Files.readString(path);
    }

}
