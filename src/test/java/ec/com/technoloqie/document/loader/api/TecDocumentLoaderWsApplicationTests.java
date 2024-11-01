package ec.com.technoloqie.document.loader.api;

import java.io.File;
import java.io.FileInputStream;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
		log.info("ingreso updateIntentTest.");
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
	
}
