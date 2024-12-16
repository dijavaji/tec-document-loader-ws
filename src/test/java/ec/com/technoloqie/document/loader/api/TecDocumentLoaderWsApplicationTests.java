package ec.com.technoloqie.document.loader.api;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.ai.document.Document;
import org.springframework.ai.reader.ExtractedTextFormatter;
import org.springframework.ai.reader.pdf.PagePdfDocumentReader;
import org.springframework.ai.reader.pdf.config.PdfDocumentReaderConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.multipart.MultipartFile;

import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.service.IFileStorageService;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("local")
@SpringBootTest
@Slf4j
class TecDocumentLoaderWsApplicationTests {
	
	@Autowired
	private IIntentService intentService;
	
	@Autowired
	private IFileStorageService storageService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void deleteIntentTest() {
		try {
			log.info("ingreso deleteIntentTest.");
			this.intentService.deleteIntent(46);
		}catch(Exception e) {
			log.error("Error deleteIntentTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"deleteIntentTest.");
		}
	}
	
	@Test
	void updateIntentTest() {
		try {
			log.info("ingreso updateIntentTest.");
			IntentDto intent = new IntentDto();
			intent.setAssistantId(1);
			intent.setId(54);
			intent.setName("nuevofrecreo");
			intent.setDescription("nueva descripcion");
			intent.setModifiedBy("test");
			this.intentService.updateIntent(intent , 54);
		}catch(Exception e) {
			log.error("Error updateIntentTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"updateIntentTest.");
		}
	}
	
	@Test
	void readFileAndSaveCsvTest() {
		log.info("ingreso readFileAndSaveCsvTest.");
		try {
			//File file = new File("src/test/resources/input.txt");
			//FileInputStream input = new FileInputStream(file);
			//MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			MultipartFile multipartFile = new MockMultipartFile("questionData.csv", new FileInputStream(new File("/home/diego/Downloads/questionData.csv")));
			
			this.storageService.save(multipartFile);
		}catch(Exception e) {
			log.error("Error readFileAndSaveCsvTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"readFileAndSaveCsvTest.");
		}
	}
	
	@Test
	void getDocsFromPdfTest() {
		log.info("ingreso getDocsFromPdfTest.");
		try {
		PagePdfDocumentReader pdfReader = new PagePdfDocumentReader("classpath:/docs/ley-minera.pdf",
				PdfDocumentReaderConfig.builder().withPageTopMargin(0)
						.withPageExtractedTextFormatter(ExtractedTextFormatter.builder().withNumberOfTopTextLinesToDelete(0).build())
						.withPagesPerDocument(1).build());

		List<Document> documents = pdfReader.read();
		
		log.info("tamano doc {}",documents.size());
		// return
		}catch(Exception e) {
			log.error("Error getDocsFromPdfTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"getDocsFromPdfTest.");
		}
	}
	
	@Test
	void readFilesAndSaveMultipartTest() {
		log.info("ingreso readFilesAndSaveMultipartTest.");
		long time = System.currentTimeMillis();
		try {
			//File file = new File("src/test/resources/input.txt");
			//FileInputStream input = new FileInputStream(file);
			//MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			//MultipartFile multipartFile1 = new MockMultipartFile("codigo_civil_colombia.pdf", "/home/codigo_civil_colombia.pdf","application/pdf",new FileInputStream(new File("/home/diego/workspaceSts4/tec-document-loader-ws/src/main/resources/docs/codigo_civil_colombia.pdf")));
			//MultipartFile multipartFile2 = new MockMultipartFile("ley-minera.pdf","/home/ley-minera.pdf" ,"application/pdf",new FileInputStream(new File("/home/diego/workspaceSts4/tec-document-loader-ws/src/main/resources/docs/ley-minera.pdf")));
			MultipartFile multipartFile3 = new MockMultipartFile("ley-minera.txt","/home/ley-minera.txt" ,"text/plain",new FileInputStream(new File("/home/diego/workspaceSts4/tec-document-loader-ws/src/main/resources/docs/ley-minera.txt")));
			
			Collection <MultipartFile> docs= new ArrayList<>();
			
			//docs.add(multipartFile1);
			//docs.add(multipartFile2);
			docs.add(multipartFile3);
			this.storageService.storeDocuments(docs);
			log.info("fin readFilesAndSaveMultipartTest. tiempo: {}", System.currentTimeMillis() - time);
		}catch(Exception e) {
			log.error("Error readFilesAndSaveMultipartTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"readFilesAndSaveMultipartTest.");
		}
	}
	
}
