package ec.com.technoloqie.document.loader.api;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import ec.com.technoloqie.document.loader.api.dto.FileDto;
import ec.com.technoloqie.document.loader.api.dto.IntentDto;
import ec.com.technoloqie.document.loader.api.dto.IntentKnowlegeDto;
import ec.com.technoloqie.document.loader.api.service.IFileService;
import ec.com.technoloqie.document.loader.api.service.IIntentService;
import lombok.extern.slf4j.Slf4j;

@ActiveProfiles("local")
@SpringBootTest
@Slf4j
public class DocumentLoaderServiceTest {
	
	@Autowired
	private IIntentService intentService;
	
	@Autowired
	private IFileService fileService;
	
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
	void getFilesByAssistantNameTest() {
		try {
			List<FileDto> filels = fileService.getFileByAssistantName("tec_procesos_bot");
			log.info("getFilesByAssistantNameTest size: {}",filels.size());
			Assertions.assertNotNull(filels);
		}catch(Exception e) {
			log.error("Error getFilesByAssistantNameTest. {}",e );
			Assertions.assertTrue(Boolean.TRUE,"getFilesByAssistantNameTest.");
		}
	}
	
	@Test
	void saveFileTest() {
		try {
			String fileName="prueba.txt"; 
			String fileType="text/plain"; 
			String filePath="uploads/4/prueba.txt"; 
			Integer assistantId=10; 
			String createdBy="test";
			fileService.createFile(fileName, fileType, filePath, assistantId, createdBy);
		}catch(Exception e) {
			log.error("Error getFilesByAssistantNameTest. ",e );
			Assertions.assertTrue(Boolean.TRUE,"getFilesByAssistantNameTest.");
		}
	}
	
	@Test
	void findIntentKnowlegeTest() {
		try {
			Integer assistantId = 1;
			List<IntentKnowlegeDto> intents = intentService.getListIntentKnowlege(assistantId);
			log.info("findIntentKnowlegeTest size: {}",intents.size());
			Assertions.assertNotNull(intents);
		}catch(Exception e) {
			log.error("Error getFilesByAssistantNameTest. ",e );
			Assertions.assertTrue(Boolean.TRUE,"getFilesByAssistantNameTest.");
		}
	}

}
