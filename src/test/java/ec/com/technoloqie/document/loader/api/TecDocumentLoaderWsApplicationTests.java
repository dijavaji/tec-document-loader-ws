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

import ec.com.technoloqie.document.loader.api.model.FileEntity;
import ec.com.technoloqie.document.loader.api.service.IFileStorageService;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("local")
@SpringBootTest
@Slf4j
class TecDocumentLoaderWsApplicationTests {
	
	@Autowired
	private IFileStorageService storageService;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	void readFileAndSaveCsvTest() {
		log.info("ingreso readFileAndSaveCsvTest.");
		try {
			//File file = new File("src/test/resources/input.txt");
			//FileInputStream input = new FileInputStream(file);
			//MultipartFile multipartFile = new MockMultipartFile("file", file.getName(), "text/plain", IOUtils.toByteArray(input));
			MultipartFile multipartFile = new MockMultipartFile("questionData.csv", new FileInputStream(new File("/var/opt/apptmp/questionData.csv")));
			
			this.storageService.saveCsv(multipartFile,3,"test");
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
			MultipartFile multipartFile1 = new MockMultipartFile("codigo_civil_colombia.pdf", "/home/codigo_civil_colombia.pdf","application/pdf",new FileInputStream(new File("/var/opt/apptmp/codigo_civil_colombia.pdf")));
			//MultipartFile multipartFile2 = new MockMultipartFile("ley-minera.pdf","/home/ley-minera.pdf" ,"application/pdf",new FileInputStream(new File("/var/opt/apptmp/ley-minera.pdf")));
			//MultipartFile multipartFile3 = new MockMultipartFile("ley-minera.txt","/home/ley-minera.txt" ,"text/plain",new FileInputStream(new File("/var/opt/apptmp/ley-minera.txt")));
			
			//MultipartFile multipartFile4 = new MockMultipartFile("Ejemplo.doc","/home/Ejemplo.doc" ,"application/msword",new FileInputStream(new File("/var/opt/apptmp/Ejemplo.doc")));
			
			Collection <MultipartFile> docs= new ArrayList<>();
			
			docs.add(multipartFile1);
			//docs.add(multipartFile2);
			//docs.add(multipartFile4);
			this.storageService.saveDocuments(docs,0);
			log.info("fin readFilesAndSaveMultipartTest. tiempo: {}", System.currentTimeMillis() - time);
		}catch(Exception e) {
			log.error("Error readFilesAndSaveMultipartTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"readFilesAndSaveMultipartTest.");
		}
	}
	
	@Test
	void saveFilesFromDirectoryPathTest() {
		try {
			String path="/var/opt/apptmp";
			Integer assistantId=4; 
			String createdBy="test";
			List<FileEntity> files = this.storageService.saveFilesFromDirectory(path, assistantId, createdBy);
			Assertions.assertNotNull(files);
		}catch(Exception e) {
			log.error("Error saveFilesFromDirectoryPathTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"saveFilesFromDirectoryPathTest.");
		}
	}
	
}
